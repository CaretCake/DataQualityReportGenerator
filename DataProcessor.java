import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

public class DataProcessor {
	private String inputFilename;
	private DataPrinter dataPrinter;
	private ArrayList<String> features = new ArrayList<String>();
	private ArrayList<ArrayList<Double>> dataInstances = new ArrayList<ArrayList<Double>>();
	private HashMap<String, ArrayList<String>> categoricalFeatureOptions = new HashMap<String, ArrayList<String>>();

	private void addFeature(String feature) {
		this.features.add(feature);
	}

	private void addCategoricalFeatureOptions(String feature, String featureOptionsString) {
		featureOptionsString = featureOptionsString.substring(1, featureOptionsString.length()-1);
		ArrayList<String> featureOptions = new ArrayList(Arrays.asList(featureOptionsString.split(",")));
		this.categoricalFeatureOptions.put(feature, featureOptions);
	}

	private int getIndexOfValueForCategoricalFeature(String categoricalFeatureName, String value) {
		return categoricalFeatureOptions.get(categoricalFeatureName).indexOf(value);
	}

	private int getCardinalityOfFeatureAtIndex(int featureIndex) {
		HashMap<Double, String> cardinalityMap =  new HashMap<Double, String>();
		for (ArrayList<Double> dataInstance : dataInstances) {
			cardinalityMap.put(dataInstance.get(featureIndex), "");
		}
		return cardinalityMap.size();
	}

	private String getNameFromIndexForCategoricalFeature(String categoricalFeatureName, int indexValue) {
		return categoricalFeatureOptions.get(categoricalFeatureName).get(indexValue);
	}

	private String getFeatureName(int featureIndex) {
		return this.features.get(featureIndex);
	}

	private String getFeatureType(int index) {
		return  (this.categoricalFeatureOptions.get(getFeatureName(index)) == null) ? "numeric" : "categorical";
	}

	private String getQuartilesOfFeatureAtIndex(int featureIndex) {
		double[] dataPoints = new double[this.dataInstances.size()];
		for (int i = 0; i < dataInstances.size(); i++) {
			dataPoints[i] = (dataInstances.get(i).get(featureIndex));
		}
		Arrays.sort(dataPoints);
		double minimum = dataPoints[0];
		double maximum = dataPoints[this.dataInstances.size() - 1];
		double firstQuartile = dataPoints[(int) Math.round(dataPoints.length * 25 / 100)];
		double median = dataPoints[(int) Math.round(dataPoints.length * 50 / 100)];
		double thirdQuartile = dataPoints[(int) Math.round(dataPoints.length * 75 / 100)];

		return minimum + "," + firstQuartile + "," + median + "," + thirdQuartile + "," + maximum;
	}

	private String getMeanAndStandardDeviationOfFeatureAtIndex(int featureIndex) {
		double mean = 0;
		double standardDeviation = 0;

		for (int i = 0; i < dataInstances.size(); i++) {
			mean += dataInstances.get(i).get(featureIndex);
		}

		mean /= this.dataInstances.size();

		for (int i = 0; i < dataInstances.size(); i++) {
			standardDeviation += Math.pow(dataInstances.get(i).get(featureIndex) - mean, 2);
		}

		standardDeviation = Math.sqrt(standardDeviation/dataInstances.size());

		return mean + "," + standardDeviation;
	}

	private String getModeDataOfCategoricalFeatureAtIndex(int featureIndex) {
		HashMap<Double, Integer> modeMap = new HashMap<Double, Integer>();
		int firstMaxOccurrences = 1;
		double firstMode = -1;
		int secondMaxOccurrences = 1;
		double secondMode = -1;

		for (int i = 0; i < dataInstances.size(); i++) {
			double dataValue = dataInstances.get(i).get(featureIndex);
			if (modeMap.get(dataValue) != null) {
				int total = modeMap.get(dataValue);
				total++;
				modeMap.put(dataValue, total);

				if (total > firstMaxOccurrences) {
					if (dataValue != firstMode) {
						secondMaxOccurrences = firstMaxOccurrences;
						secondMode = firstMode;
					}
					firstMaxOccurrences = total;
					firstMode = dataValue;
				} else if (total > secondMaxOccurrences && dataValue != firstMode) {
					secondMaxOccurrences = total;
					secondMode = dataValue;
				}
			} else {
				modeMap.put(dataValue, 1);
			}
		}

		if (firstMaxOccurrences == 1) {
			return "N/A,N/A,N/A,N/A,N/A,N/A";
		} else if (secondMaxOccurrences == 1) {
			return getNameFromIndexForCategoricalFeature(getFeatureName(featureIndex), (int)firstMode) + "," + modeMap.get(firstMode) + "," + (((double)modeMap.get(firstMode)/(double)this.dataInstances.size())*100) + "N/A,N/A,N/A";
		}

		return getNameFromIndexForCategoricalFeature(getFeatureName(featureIndex), (int)firstMode) + "," + modeMap.get(firstMode) + "," + (((double)modeMap.get(firstMode)/(double)this.dataInstances.size())*100) + "," + getNameFromIndexForCategoricalFeature(getFeatureName(featureIndex), (int)secondMode) + "," + modeMap.get(secondMode) + "," + (((double)modeMap.get(secondMode)/(double)this.dataInstances.size())*100);
	}

	private String processNumericFeatureData(int featureIndex) {
		String featureName = getFeatureName(featureIndex);
		String featureData = featureName+",";
		featureData += getCardinalityOfFeatureAtIndex(featureIndex) + ",";
		featureData += getQuartilesOfFeatureAtIndex(featureIndex) + ",";
		featureData += getMeanAndStandardDeviationOfFeatureAtIndex(featureIndex);

		return featureData;
	}

	private String processCategoricalFeatureData(int featureIndex) {
		String featureName = getFeatureName(featureIndex);
		String featureData = featureName+",";
		featureData += getCardinalityOfFeatureAtIndex(featureIndex) + ",";
		featureData += getModeDataOfCategoricalFeatureAtIndex(featureIndex);

		return featureData;
	}

	public void addDataInstance(String dataInstance) {
		String[] data = dataInstance.split(",");
		ArrayList<Double> processedDataInstance = new ArrayList<Double>();
		double processedValue = 0;
		for (int i = 0; i < data.length; i++) {
			if (getFeatureType(i) == "categorical") {
				processedValue = getIndexOfValueForCategoricalFeature(getFeatureName(i), data[i]);
				processedDataInstance.add(processedValue);
			} else {
				processedValue = Double.parseDouble(data[i]);
				processedDataInstance.add(processedValue);
			}
		}
		this.dataInstances.add(processedDataInstance);
	}

	public void parseDataFromArffFile() throws Exception {
		try {
			Scanner in = new Scanner(new File(inputFilename), "UTF-8");
			in.nextLine();

			while(in.hasNextLine()) {
				String line = in.nextLine().trim();
				if (line.length() > 0 && line.charAt(0) != '%' && !line.contains("@data") && !line.contains("@relation")) {
					if (line.contains("@attribute")) {
						String[] lineSplitOnSpace = line.split(" ");
						switch (lineSplitOnSpace[2].trim()) {
							case "numeric":
								this.addFeature(lineSplitOnSpace[1].trim());
								break;
							default:
								this.addFeature(lineSplitOnSpace[1].trim());
								this.addCategoricalFeatureOptions(lineSplitOnSpace[1].trim(), lineSplitOnSpace[2].trim());
								break;
						}
					} else {
						this.addDataInstance(line);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Issue reading in input file:" + e);
			System.exit(0);
		}
	}

	public void generateDataQualityReport() {
		for (int i = 0; i < this.features.size(); i++) {
			if (getFeatureType(i) == "numeric") {
				dataPrinter.addNumericFeatureData(processNumericFeatureData(i));
			} else if (getFeatureType(i) == "categorical") {
				dataPrinter.addCategoricalFeatureData(processCategoricalFeatureData(i));
			}
		}

		dataPrinter.setTotalNumberOfInstances(this.dataInstances.size());
	}

	public void setInputFilename(String filename) {
		this.inputFilename = filename;
	}

	public void setDataPrinter(DataPrinter dataPrinter) {
		this.dataPrinter = dataPrinter;
	}

}

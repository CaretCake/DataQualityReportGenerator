import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class DataProcessor {
	private String inputFilename;
	private DataPrinter dataPrinter;
	private ArrayList<String> features = new ArrayList<String>();
	private ArrayList<ArrayList<Double>> dataInstances = new ArrayList<ArrayList<Double>>();
	private HashMap<String, ArrayList<String>> categoricalFeatureOptions = new HashMap<String, ArrayList<String>>();

	public void setInputFilename(String filename) {
		this.inputFilename = filename;
	}

	public void setDataPrinter(DataPrinter dataPrinter) {
		this.dataPrinter = dataPrinter;
	}

	private int getIndexOfValueForCategoricalFeature(String categoricalFeatureName, String value) {
		return categoricalFeatureOptions.get(categoricalFeatureName).indexOf(value);
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

	private void addFeature(String feature) {
		this.features.add(feature);
	}

	private void addCategoricalFeatureOptions(String feature, String featureOptionsString) {
		featureOptionsString = featureOptionsString.substring(1, featureOptionsString.length()-1);
		ArrayList<String> featureOptions = new ArrayList(Arrays.asList(featureOptionsString.split(",")));
		this.categoricalFeatureOptions.put(feature, featureOptions);
	}

	public void addDataInstance(String dataInstance) {
		String[] data = dataInstance.split(",");
		ArrayList<Double> processedDataInstance = new ArrayList<Double>();
		double processedValue = 0;
		for (int i = 0; i < data.length; i++) {
			if (getFeatureType(i) == "categorical") {
				processedValue = getIndexOfValueForCategoricalFeature(getFeatureName(i), data[i]);
				processedDataInstance.add(processedValue);
			}
			else {
				processedValue = Double.parseDouble(data[i]);
				processedDataInstance.add(processedValue);
			}
		}
		this.dataInstances.add(processedDataInstance);
		/*System.out.println(this.dataInstances);
		System.out.println(this.categoricalFeatureOptions);*/
	}

	public void parseDataFromArffFile() throws Exception {
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
				}
				else {
					this.addDataInstance(line);
				}
			}
		}
	}

	public void generateDataQualityReport() {
		for (int i = 0; i < this.features.size(); i++) {
			if (getFeatureType(i) == "numeric") {
				dataPrinter.addNumericFeatureData(processNumericFeatureData(i));
			}
			else if (getFeatureType(i) == "categorical") {
				dataPrinter.addCategoricalFeatureData(processCategoricalFeatureData(i));
			}
		}
		// Process data here, which will store everything in arraylists in dataprinter
		// then call print methods for all that will print headers, data, etc for each?

	}

	private String processNumericFeatureData(int featureIndex) {
		String featureName = getFeatureName(featureIndex);
		String featureData = featureName+",";
		//TODO: Percent missing
		featureData += "?,";
		//TODO: Cardinality
		featureData += getCardinalityOfFeatureAtIndex(featureIndex) + ",";
		//TODO: Minimum
		featureData += getQuartilesAndMeanOfFeatureAtIndex(featureIndex) + ",";
		//TODO: 1st Quartile
		//TODO: Mean
		//TODO: Median
		//TODO: 3rd Quartile
		//TODO: Maximium
		//TODO: Standard Deviation

		return featureData;
	}

	private String processCategoricalFeatureData(int featureIndex) {
		String featureName = getFeatureName(featureIndex);
		String featureData = featureName+",";
		//TODO: Percent missing
		featureData += "?,";
		//TODO: Cardinality
		featureData += getCardinalityOfFeatureAtIndex(featureIndex);
		//TODO: Mode
		//TODO: Mode frequency
		//TODO: Mode percent
		//TODO: Second mode
		//TODO: Second mode frequency
		//TODO: Second mode percent

		return featureData;
	}

	private int getCardinalityOfFeatureAtIndex(int featureIndex) {
		HashMap<Double, String> cardinalityMap =  new HashMap<Double, String>();
		for (ArrayList<Double> dataInstance : dataInstances) {
			cardinalityMap.put(dataInstance.get(featureIndex), "");
		}
		return cardinalityMap.size();
	}

	private String getQuartilesAndMeanOfFeatureAtIndex(int featureIndex) {
		double[] dataPoints = new double[this.dataInstances.size()];
		double mean = 0;
		for (int i = 0; i < dataInstances.size(); i++) {
			dataPoints[i] = (dataInstances.get(i).get(featureIndex));
			mean += dataInstances.get(i).get(featureIndex);
		}
		Arrays.sort(dataPoints);
		double minimum = dataPoints[0];
		double maximum = dataPoints[this.dataInstances.size() - 1];
		double firstQuartile = dataPoints[(int) Math.round(dataPoints.length * 25 / 100)];
		double median = dataPoints[(int) Math.round(dataPoints.length * 50 / 100)];
		double thirdQuartile = dataPoints[(int) Math.round(dataPoints.length * 75 / 100)];

		mean /= this.dataInstances.size();

		return minimum + "," + firstQuartile + "," + mean + "," + median + "," + thirdQuartile + "," + maximum;
	}
}

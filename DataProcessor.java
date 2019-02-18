import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class DataProcessor {
	private String inputFilename;
	private ArrayList<String> features = new ArrayList<String>();
	private ArrayList<ArrayList<Double>> dataInstances = new ArrayList<ArrayList<Double>>();
	private HashMap<String, ArrayList<String>> categoricalFeatureOptions = new HashMap<String, ArrayList<String>>();

	public void setInputFilename(String filename) {
		inputFilename = filename;
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

	public void generateDataQualityReport(String outputFilename)  throws Exception {
		PrintWriter out = new PrintWriter(outputFilename);
		DataPrinter dataPrinter = new DataPrinter();
		dataPrinter.setPrintWriter(out);

		processData();

		// Process data here, which will store everything in arraylists in dataprinter
		// then call print methods for all that will print headers, data, etc for each?

		dataPrinter.printReport(this.dataInstances.size());
		out.close();
	}

	public void processData() {
		//Iterate through features, determine if feature at index is numeric/categorical
		//and if it's numeric, calculate following for feature:
			//TODO: Numeric feature
			//TODO: Percent missing
			//TODO: Cardinality
			//TODO: Minimum
			//TODO: 1st Quartile
			//TODO: Mean
			//TODO: Median
			//TODO: 3rd Quartile
			//TODO: Maximium
			//TODO: Standard Deviation
			//Store entry for future printing in DataPrinter
		//else if it's categorical calculate following:
			//TODO: Categorical feature
			//TODO: Percent missing
			//TODO: Cardinality
			//TODO: Mode
			//TODO: Mode frequency
			//TODO: Mode percent
			//TODO: Second mode
			//TODO: Second mode frequency
			//TODO: Second mode percent
			//Store entry for future printing in DataPrinter
	}
}

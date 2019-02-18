
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.*;

public class DataProcessor {
	private ArrayList<String> features = new ArrayList<String>();
	private ArrayList<Double> dataInstances = new ArrayList<Double>();
	private HashMap<String, ArrayList<String>> categoricalFeatureOptions = new HashMap<String, ArrayList<String>>();

	public void addNumericFeature(String feature) {
		this.features.add(feature);
	}

	public void addCategoricalFeature(String feature, String featureOptionsString) {
		this.features.add(feature);
		featureOptionsString = featureOptionsString.substring(1, featureOptionsString.length()-1);
		ArrayList<String> featureOptions = new ArrayList(Arrays.asList(featureOptionsString.split(",")));
		this.categoricalFeatureOptions.put(feature, featureOptions);
	}

	public void addDataInstance(String dataInstance) {
		//ArrayList<String> data = (ArrayList<String>)Arrays.asList(dataInstance.split(","));

		//this.dataInstances.add();
	}

	public void generateDataQualityReport(String outputFilename)  throws Exception {
		PrintWriter out = new PrintWriter(outputFilename);
		DataPrinter dataPrinter = new DataPrinter();
		dataPrinter.setPrintWriter(out);
		dataPrinter.printTotalInstances(this.dataInstances.size());

		processData();

		dataPrinter.printNumericHeader();
		//TODO: process and print data for numerical features
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
		dataPrinter.printCategoricalHeader();
		//TODO: process and print data for categorical features
		//TODO: Categorical feature
		//TODO: Percent missing
		//TODO: Cardinality
		//TODO: Mode
		//TODO: Mode frequency
		//TODO: Mode percent
		//TODO: Second mode
		//TODO: Second mode frequency
		//TODO: Second mode percent
		out.close();
	}

	public void processData() {
		//TODO: Write method
	}
}

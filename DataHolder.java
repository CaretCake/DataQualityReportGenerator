
import java.util.ArrayList;
import java.io.*;

public class DataHolder {
	private ArrayList<String> features = new ArrayList<String>();
	private ArrayList<String> categoricalFeatureOptions = new ArrayList<String>();
	private ArrayList<String> dataInstances = new ArrayList<String>();

	public void generateDataQualityReport(String outputFilename)  throws Exception {
		PrintWriter out = new PrintWriter(outputFilename);
		printTotalInstances(this.dataInstances.size(), out);



		out.close();
	}

	public void addNumericFeature(String feature) {
		this.features.add(feature);
	}

	public void addCategoricalFeature(String feature, String featureOptions) {
		this.features.add(feature);
		this.categoricalFeatureOptions.add(featureOptions);
	}

	public void addDataInstance(String dataInstance) {
		this.dataInstances.add(dataInstance);
	}
}
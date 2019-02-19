import java.io.*;
import java.util.ArrayList;

public class DataPrinter {
	private String NUMERIC_HEADER = "Numeric feature,Cardinality,Minimum,1st Quartile,Median,3rd Quartile,Maximium,Mean,Standard Deviation";
	private String CATEGORICAL_HEADER = "Categorical feature,Cardinality,Mode,Mode frequency,Mode percent,Second mode,Second mode frequency,Second mode percent";
	private ArrayList<String> numericFeatureData = new ArrayList<String>();
	private ArrayList<String> categoricalFeatureData = new ArrayList<String>();
	private int totalNumberOfInstances;
	private PrintWriter out;

	public void addNumericFeatureData(String commaSeparatedData) {
		this.numericFeatureData.add(commaSeparatedData);
	}

	public void addCategoricalFeatureData(String commaSeparatedData) {
		this.categoricalFeatureData.add(commaSeparatedData);
	}

	public void setTotalNumberOfInstances(int numberOfInstances) {
		totalNumberOfInstances = numberOfInstances;
	}

	private void printTotalInstances() {
		out.println("Instances\n" + totalNumberOfInstances);
	}

	private void printNumericHeader() {
		out.println(NUMERIC_HEADER);
	}

	private void printCategoricalHeader() {
		out.println(CATEGORICAL_HEADER);
	}

	private void printNumericFeatureData() {
		for (String dataInstance : numericFeatureData) {
			out.println(dataInstance);
		}
	}

	private void printCategoricalFeatureData() {
		for (String dataInstance : categoricalFeatureData) {
			out.println(dataInstance);
		}
	}

	private void printBlankLine() {
		out.println();
	}

	public void printDataQualityReport(String outputFilename) throws Exception {
		out = new PrintWriter(outputFilename);
		DataPrinter dataPrinter = new DataPrinter();

		this.printTotalInstances();
		this.printBlankLine();
		this.printNumericHeader();
		this.printNumericFeatureData();
		this.printBlankLine();
		this.printCategoricalHeader();
		this.printCategoricalFeatureData();

		out.close();
	}

}

import java.io.*;

public class DataPrinter {
	private String NUMERIC_HEADER = "Numeric feature,Percent missing,Cardinality,Minimum,1st Quartile,Mean,Median,3rd Quartile,Maximium,Standard Deviation";
	private String CATEGORICAL_HEADER = "Categorical feature,Percent missing,Cardinality,Mode,Mode frequency,Mode percent,Second mode,Second mode frequency,Second mode percent";
	private PrintWriter out;

	public void setPrintWriter(PrintWriter printWriter) {
		out = printWriter;
	}

	public void printTotalInstances (int numberOfInstances) {
		out.println("Instances\n" + numberOfInstances + "\n");
	}

	public void printNumericHeader() {
		out.println(NUMERIC_HEADER);
	}

	public void printCategoricalHeader() {
		out.println(CATEGORICAL_HEADER);
	}

	public void printReport(int numberOfInstances) {
		this.printTotalInstances(numberOfInstances);

		this.printNumericHeader();
		// print numeric data for each numeric feature
		this.printCategoricalHeader();
		// print categorical data for each categorical feature
	}

}
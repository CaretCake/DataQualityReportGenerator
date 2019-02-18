public class DataPrinter {
	private String NUMERIC_HEADER = "Numeric feature,Percent missing,Cardinality,Minimum,1st Quartile,Mean,Median,3rd Quartile,Maximium,Standard Deviation";
	private String CATEGORICAL_HEADER = "Categorical feature,Percent missing,Cardinality,Mode,Mode frequency,Mode percent,Second mode,Second mode frequency,Second mode percent";

	public void printTotalInstances (int numberOfInstances, PrintWriter out) {
		out.println("Instances\n" + numberOfInstances + "\n");
	}

}
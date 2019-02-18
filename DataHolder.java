
import java.util.ArrayList;
import java.io.*;

public class DataHolder {
	ArrayList<String> attributes = new ArrayList<String>();
	ArrayList<String> dataInstances = new ArrayList<String>();

	public void generateDataQualityReport(String outputFilename)  throws Exception {
		PrintWriter out = new PrintWriter(outputFilename);
		out.println("Instances\n" + this.dataInstances.size());
		out.close();
	}
}
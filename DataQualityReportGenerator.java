// Melissa Farrell

import java.io.*;

public class DataQualityReportGenerator {
	public static void main(String[] args) throws Exception {

		DataProcessor dataProcessor = new DataProcessor();

		//TODO: un-hardcode the filename
		dataProcessor.setInputFilename("lakes.arff");
		dataProcessor.parseDataFromArffFile();
		dataProcessor.generateDataQualityReport("lakesDQR.csv");

	}
}
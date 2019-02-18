// Melissa Farrell

import java.io.*;

public class DataQualityReportGenerator {
	public static void main(String[] args) throws Exception {

		DataProcessor dataProcessor = new DataProcessor();
		DataPrinter dataPrinter = new DataPrinter();

		//TODO: un-hardcode the filename
		dataProcessor.setInputFilename("lakes.arff");
		dataProcessor.setDataPrinter(dataPrinter);
		dataProcessor.parseDataFromArffFile();
		dataProcessor.generateDataQualityReport();
		dataPrinter.printDataQualityReport("lakesDQR.csv");

	}
}
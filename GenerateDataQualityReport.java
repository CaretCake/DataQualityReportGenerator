// Melissa Farrell
// Run with command: java GenerateDataQualityReport "lakes.arff"

public class DataQualityReportGenerator {
	public static void main(String[] args) throws Exception {

		DataProcessor dataProcessor = new DataProcessor();
		DataPrinter dataPrinter = new DataPrinter();

		dataProcessor.setInputFilename(args[0]);
		dataProcessor.setDataPrinter(dataPrinter);
		dataProcessor.parseDataFromArffFile();
		dataProcessor.generateDataQualityReport();
		dataPrinter.printDataQualityReport(args[0].substring(0, args[0].indexOf('.')) + "DQR.csv");

	}
}

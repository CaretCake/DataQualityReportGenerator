// Melissa Farrell

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DataQualityReportGenerator {
	public static void main(String[] args) throws Exception {

		DataHolder dataHolder = new DataHolder();

		//TODO: un-hardcode the filename
		readInArffFile("lakes.arff", dataHolder);
		dataHolder.generateDataQualityReport("lakesDQR.csv");

	}

	public static void readInArffFile(String filename, DataHolder dataHolder) throws Exception {
		Scanner in = new Scanner(new File(filename), "UTF-8");
 		in.nextLine();

		while(in.hasNextLine()) {
			String line = in.nextLine().trim();
			if (line.length() > 0 && line.charAt(0) != '%' && !line.contains("@data") && !line.contains("@relation")) {
				if (line.contains("@attribute")) {
					String[] lineSplitOnSpace = line.split(" ");
					//TODO: make method for adding attribute
					dataHolder.attributes.add(lineSplitOnSpace[1] + "," + lineSplitOnSpace[2]);
				}
				else {
					//TODO: make method for adding data instance
					dataHolder.dataInstances.add(line);
				}
			}
		}
	}
}
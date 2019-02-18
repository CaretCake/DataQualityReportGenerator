// Melissa Farrell

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DataQualityReportGenerator {
	public static void main(String[] args) throws Exception {

		DataHolder dataHolder = new DataHolder();

		readInArffFile("lakes.arff", dataHolder);

		//System.out.println(dataHolder.attributes.get(0));

	}

	public static void readInArffFile(String filename, DataHolder dataHolder) throws Exception {
		Scanner in = new Scanner(new File(filename), "UTF-8");
 		in.nextLine();

		while(in.hasNextLine()) {
			String line = in.nextLine().trim();
			if (line.length() > 0 && line.charAt(0) != '%' && !line.contains("@data") && !line.contains("@relation")) {
				if (line.contains("@attribute")) {
					String[] lineSplitOnSpace = line.split(" ");
					dataHolder.attributes.add(lineSplitOnSpace[1] + "," + lineSplitOnSpace[2]);
				}
				else {
					dataHolder.dataInstances.add(line);
				}
			}
		}
	}
}
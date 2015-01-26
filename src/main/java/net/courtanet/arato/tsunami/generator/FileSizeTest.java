package net.courtanet.arato.tsunami.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileSizeTest {

	public static void main(String[] args) throws IOException {
		File doc = new File("test.txt");
		doc.createNewFile();

		showSize(doc);
		OutputStreamWriter out = new FileWriter(doc);
		for (int i = 0; i < 20000; i++)
			out.append("test");
		out.close();
		showSize(doc);
		doc.delete();
	}

	private static void showSize(File doc) {
		double bytes = doc.length();
		double kilobytes = (bytes / 1024);
		double megabytes = (kilobytes / 1024);
		double gigabytes = (megabytes / 1024);

		System.out.println(bytes);
		System.out.println(kilobytes);
		System.out.println(megabytes);
		System.out.println(gigabytes);
	}

}

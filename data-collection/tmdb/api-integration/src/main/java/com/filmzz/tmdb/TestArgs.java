package com.filmzz.tmdb;

import java.io.File;

public class TestArgs {
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Pass in the IMDB movie file");
		}

		String filePath = args[0];
		File inputFile = new File(filePath);
		if (!inputFile.exists()) {
			throw new IllegalArgumentException(String.format("The IMDB file: %s does not exist", filePath));
		}

		if (!inputFile.isFile()) {
			throw new IllegalArgumentException(String.format("The IMDB file: %s is a directory", filePath));
		}

		if (!inputFile.canRead()) {
			throw new IllegalArgumentException(String.format("The IMDB file: %s should be readable", filePath));
		}

		File directory = inputFile.getParentFile();
		try {
			File aggregatedOutputFile = createOutputFile(directory, inputFile, "-aggregated");
			File nonAggregatedOutputFile = createOutputFile(directory, inputFile, "-nonaggregated");
			File errorLogOutputFile = createOutputFile(directory, inputFile, "-errorlog");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static File createOutputFile(File directory, File inputFile, String suffix) throws Exception {
		File outputFile = new File(directory.getAbsolutePath() + File.separator + inputFile.getName() + suffix);
		if (outputFile.exists()) {
			System.out.println("The file:" + outputFile.getAbsolutePath() + " already exists. Deleting it");
			outputFile.delete();
		}

		System.out.println("Creating the file:" + outputFile.getAbsolutePath());
		outputFile.createNewFile();
		return outputFile;
	}

}

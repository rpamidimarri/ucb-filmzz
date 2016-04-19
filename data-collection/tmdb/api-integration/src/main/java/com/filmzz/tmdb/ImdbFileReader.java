package com.filmzz.tmdb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.filmzz.tmdb.model.OmdbMovie;
import com.filmzz.tmdb.persistence.Movie;
import com.filmzz.tmdb.persistence.MovieService;

import au.com.bytecode.opencsv.CSVWriter;

public class ImdbFileReader {
	private Pattern pattern;
	private Pattern yearPattern;
	private MovieService movieService;

	public ImdbFileReader() {
		pattern = Pattern.compile("([\\d\\.]{10})\\s+(\\d+)\\s+(\\d\\.\\d)\\s+(.*)");
		//Year is present in all the lines in the imdb file. The format is "MovieTitle" (YYYY). The pattern below is to parse out 
		//the MovieTitle and the year 
		yearPattern = Pattern.compile("(.*)\\((\\d{4})\\).*");
		movieService = new MovieService();
	}

	public void parseImdbFile(File imdbFile, CSVWriter aggregatedWriter, 
			CSVWriter nonAggregatedWriter,
			CSVWriter errorLogWriter) throws IOException {
		long startTime = System.currentTimeMillis();
		OmdbApiClient omdbApiClient = new OmdbApiClient();
		List<String> lines = FileUtils.readLines(imdbFile);
		Matcher matcher;
		int numberOfOmdbApiCalls = 0;
		for (String line : lines) {
			System.out.println("Processing line:" + line);
			matcher = pattern.matcher(line);
			if (matcher.matches()) {
				String ratingsDistribution = matcher.group(1);
				String numberOfRatings = matcher.group(2);
				String averageRating = matcher.group(3);
				String titleAndYear = matcher.group(4);

				String title = null;
				String year = null;
				if (titleAndYear.startsWith("#")) {
					//Starts with #. Remove them - Else, searching in OMDB will be a problem
					System.out.println("The entry=" + titleAndYear + " begins with #..Removing the #");
					titleAndYear = titleAndYear.replaceAll("#", "");
				}

				matcher = yearPattern.matcher(titleAndYear);
				boolean makeApiCallToOmdb = false;
				if (matcher.matches()) {
					title = matcher.group(1).trim();
					year = matcher.group(2).trim();
					if (Integer.valueOf(numberOfRatings) > 50 || Integer.valueOf(year) > 2013) {
						makeApiCallToOmdb = true;
					}
				}

				if (title != null) {
					try {
						OmdbMovie omdbMovie = null;
						if (makeApiCallToOmdb) {
							omdbMovie = omdbApiClient.getOmdbMovie(title, "movie", year);
							numberOfOmdbApiCalls++;
							System.out.println("Number of OmdbApiCalls:" + numberOfOmdbApiCalls);
						} else {
							System.out.println("Not making a call to OMDB for the movie:" + titleAndYear);
						}

						//Write to the DB
						Movie movie = new Movie(titleAndYear, title, year, Long.valueOf(numberOfRatings),
								Float.valueOf(averageRating), ratingsDistribution,
								omdbMovie);

						System.out.println("Saving movie with the titleAndYear:" + titleAndYear + " to the DB. ImdbId=" + movie.getImdbId());
						movieService.createMovie(movie);

						if (omdbMovie != null) {
							writeToAggregatedCsv(aggregatedWriter, titleAndYear, numberOfRatings, 
									averageRating, ratingsDistribution,
									title, year,
									omdbMovie);
						} else {
							System.out.println("Cannot get the OMDB movie information for the movie=" + titleAndYear + " and writing this line to the non-aggregated file");
							nonAggregatedWriter.writeNext(new String[] {titleAndYear, numberOfRatings, averageRating, ratingsDistribution});
						}
					} catch (Exception e) {
						e.printStackTrace();
						errorLogWriter.writeNext(new String[] {line});
					}
				} else {
					System.out.println("Cannot get the OMDB movie information for the movie=" + titleAndYear + " and writing this line to the non-aggregated file");
					nonAggregatedWriter.writeNext(new String[] {titleAndYear, numberOfRatings, averageRating, ratingsDistribution});
				}
			}
		}

		aggregatedWriter.flush();
		aggregatedWriter.close();

		nonAggregatedWriter.flush();
		nonAggregatedWriter.close();

		errorLogWriter.flush();
		errorLogWriter.close();

		System.out.println("Total Number of OmdbApiCalls:" + numberOfOmdbApiCalls);
		long currentTime = System.currentTimeMillis();
		System.out.println("The parsing of IMDB file took " + (currentTime - startTime)/1000 + " seconds");
	}

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

		CSVWriter aggregatedWriter = null;
		CSVWriter nonAggregatedWriter = null;
		CSVWriter errorLogWriter = null;

		File directory = inputFile.getParentFile();
		try {
			File aggregatedOutputFile = createOutputFile(directory, inputFile, "-aggregated");
			aggregatedWriter = createCsvWriter(aggregatedOutputFile);
			File nonAggregatedOutputFile = createOutputFile(directory, inputFile, "-nonaggregated");
			nonAggregatedWriter = createCsvWriter(nonAggregatedOutputFile);
			File errorLogOutputFile = createOutputFile(directory, inputFile, "-errorlog");
			errorLogWriter = createCsvWriter(errorLogOutputFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ImdbFileReader fileReader = new ImdbFileReader();
		try {
			fileReader.parseImdbFile(inputFile, aggregatedWriter, 
					nonAggregatedWriter, errorLogWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

	private CSVWriter createCsvWriter(String filePath) throws IOException {
		File outFile = new File(filePath);
		outFile.delete();
		outFile.createNewFile();
		OutputStream outStream = FileUtils.openOutputStream(outFile);
		CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outStream, "UTF-8"));
		return csvWriter;
	}

	private static CSVWriter createCsvWriter(File file) throws IOException {
		OutputStream outStream = FileUtils.openOutputStream(file);
		CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outStream, "UTF-8"));
		return csvWriter;
	}

	private void writeToAggregatedCsv(CSVWriter aggregatedWriter, String titleAndYear, String numberOfRatings,
			String averageRating, String ratingsDistribution, String title, String year, 
			OmdbMovie omdbMovie) {
		aggregatedWriter.writeNext(new String[] {titleAndYear, numberOfRatings, 
				averageRating, ratingsDistribution,
				title, year,
				omdbMovie.getImdbID(),
				omdbMovie.getActors(), omdbMovie.getAwards(), 
				omdbMovie.getBoxOffice(), omdbMovie.getCountry(), 
				omdbMovie.getDirector(), omdbMovie.getdVD(), 
				omdbMovie.getGenre(), omdbMovie.getLanguage(),
				omdbMovie.getMetascore(), omdbMovie.getPlot(),
				omdbMovie.getPoster(),omdbMovie.getProduction(),
				omdbMovie.getRated(), omdbMovie.getReleased(),
				omdbMovie.getRuntime(), omdbMovie.getTitle(),
				omdbMovie.getTomatoConsensus(), omdbMovie.getTomatoFresh(),
				omdbMovie.getTomatoImage(), omdbMovie.getTomatoMeter(),
				omdbMovie.getTomatoRating(), omdbMovie.getTomatoReviews(),
				omdbMovie.getTomatoRotten(), omdbMovie.getTomatoURL(),
				omdbMovie.getTomatoUserMeter(), omdbMovie.getTomatoUserRating(),
				omdbMovie.getTomatoUserReviews()});
	}
}

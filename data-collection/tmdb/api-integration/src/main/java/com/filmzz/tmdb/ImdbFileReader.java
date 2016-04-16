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

import au.com.bytecode.opencsv.CSVWriter;

public class ImdbFileReader {
	private static final String BASE_URL_SEARCH = "https://api.themoviedb.org/3/movie";
	private static final String OUTPUT_FILE_PATH_AGGREGATED = "/Users/Raghu/ucb-filmzz/data-collection/imdb/clean-data/aggregated-ratings-1"; 
	private static final String OUTPUT_FILE_PATH_NON_AGGREGATED = "/Users/Raghu/ucb-filmzz/data-collection/imdb/clean-data/non-aggregated-ratings-1";
	private static final String OUTPUT_FILE_PATH_TV_SERIES = "/Users/Raghu/ucb-filmzz/data-collection/imdb/clean-data/tv-series-1"; 

	
	private String filePath;
	private Pattern pattern;
	private Pattern yearPattern;
	
	public ImdbFileReader() {
		filePath = "/Users/Raghu/ucb-filmzz/data-collection/imdb/clean-data/ratings1";
		pattern = Pattern.compile("([\\d\\.]{10})\\s+(\\d+)\\s+(\\d\\.\\d)\\s+(.*)");
		//Year is present in all the lines in the imdb file. The format is "MovieTitle" (YYYY). The pattern below is to parse out 
		//the MovieTitle and the year 
		yearPattern = Pattern.compile("(.*)\\((\\d{4})\\).*");
	}
	
	public void parseImdbFile() throws IOException {
		System.out.println("Time at start:" + new Date().toString());
		CSVWriter aggregatedWriter = createCsvWriter(OUTPUT_FILE_PATH_AGGREGATED);
		CSVWriter nonAggregatedWriter = createCsvWriter(OUTPUT_FILE_PATH_NON_AGGREGATED);
		CSVWriter tvSeriesWriter = createCsvWriter(OUTPUT_FILE_PATH_TV_SERIES);
		
		OmdbApiClient omdbApiClient = new OmdbApiClient();
		File imdbFile = new File(filePath);
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
				if (titleAndYear.contains("{") && titleAndYear.contains("}")) {
					//This is a TV series..ignore
					System.out.println("Ignoring the entry=" + titleAndYear + " as it is a TV series");
					tvSeriesWriter.writeNext(new String[] {titleAndYear, numberOfRatings, averageRating, ratingsDistribution});
					continue;
				}
				
				String title = null;
				String year = null;
				if (titleAndYear.startsWith("\"")) {
					//Starts with quotes. Remove them - Else, searching in OMDB will be a problem
					System.out.println("The entry=" + titleAndYear + " has quotes in it..Removing the quotes");
					titleAndYear = titleAndYear.replaceAll("\"", "");
				}
				
				matcher = yearPattern.matcher(titleAndYear);
				if (matcher.matches()) {
					title = matcher.group(1);
					year = matcher.group(2);
				}
				
				if (title != null) {
					OmdbMovie movie = omdbApiClient.getOmdbMovie(title, "movie", year);
					numberOfOmdbApiCalls++;
					System.out.println("Number of OmdbApiCalls:" + numberOfOmdbApiCalls);
					aggregatedWriter.writeNext(new String[] {titleAndYear, numberOfRatings, 
							averageRating, ratingsDistribution,
							title, year,
							movie.getImdbID(),
							movie.getActors(), movie.getAwards(), 
							movie.getBoxOffice(), movie.getCountry(), 
							movie.getDirector(), movie.getdVD(), 
							movie.getGenre(), movie.getLanguage(),
							movie.getMetascore(), movie.getPlot(),
							movie.getPoster(), movie.getProduction(),
							movie.getRated(), movie.getReleased(),
							movie.getRuntime(), movie.getTitle(),
							movie.getTomatoConsensus(), movie.getTomatoFresh(),
							movie.getTomatoImage(), movie.getTomatoMeter(),
							movie.getTomatoRating(), movie.getTomatoReviews(),
							movie.getTomatoRotten(), movie.getTomatoURL(),
							movie.getTomatoUserMeter(), movie.getTomatoUserRating(),
							movie.getTomatoUserReviews()});
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
		
		tvSeriesWriter.flush();
		tvSeriesWriter.close();
		
		System.out.println("Total Number of OmdbApiCalls:" + numberOfOmdbApiCalls);
		System.out.println("Time when we return:" + new Date().toString());
	}
	
	public static void main(String[] args) {
		ImdbFileReader fileReader = new ImdbFileReader();
		try {
			fileReader.parseImdbFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private CSVWriter createCsvWriter(String filePath) throws IOException {
		File outFile = new File(filePath);
		outFile.delete();
		outFile.createNewFile();
		OutputStream outStream = FileUtils.openOutputStream(outFile);
		CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outStream, "UTF-8"));
		return csvWriter;
	}
}

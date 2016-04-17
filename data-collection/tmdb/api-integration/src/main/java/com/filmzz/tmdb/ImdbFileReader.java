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
	private static final String BASE_URL_SEARCH = "https://api.themoviedb.org/3/movie";
	private static final String OUTPUT_FILE_PATH_AGGREGATED = "/Users/Raghu/imdb-data-store/clean-data/test/aggregated-movie-ratings-1"; 
	private static final String OUTPUT_FILE_PATH_NON_AGGREGATED = "/Users/Raghu/imdb-data-store/clean-data/test/non-aggregated-movie-ratings-1";
	private static final String OUTPUT_FILE_PATH_ERROR_LOG = "/Users/Raghu/imdb-data-store/clean-data/test/error-log-1";

	private String filePath;
	private Pattern pattern;
	private Pattern yearPattern;
	private MovieService movieService;
	
	public ImdbFileReader() {
		filePath = "/Users/Raghu/imdb-data-store/clean-data/test/movie-ratings-1";
		pattern = Pattern.compile("([\\d\\.]{10})\\s+(\\d+)\\s+(\\d\\.\\d)\\s+(.*)");
		//Year is present in all the lines in the imdb file. The format is "MovieTitle" (YYYY). The pattern below is to parse out 
		//the MovieTitle and the year 
		yearPattern = Pattern.compile("(.*)\\((\\d{4})\\).*");
		movieService = new MovieService();
	}
	
	public void parseImdbFile() throws IOException {
		long startTime = System.currentTimeMillis();
		CSVWriter aggregatedWriter = createCsvWriter(OUTPUT_FILE_PATH_AGGREGATED);
		CSVWriter nonAggregatedWriter = createCsvWriter(OUTPUT_FILE_PATH_NON_AGGREGATED);
		CSVWriter errorLogWriter = createCsvWriter(OUTPUT_FILE_PATH_ERROR_LOG);
		
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
				
				String title = null;
				String year = null;
				if (titleAndYear.startsWith("#")) {
					//Starts with #. Remove them - Else, searching in OMDB will be a problem
					System.out.println("The entry=" + titleAndYear + " begins with #..Removing the #");
					titleAndYear = titleAndYear.replaceAll("#", "");
				}
				
				matcher = yearPattern.matcher(titleAndYear);
				if (matcher.matches()) {
					title = matcher.group(1);
					year = matcher.group(2);
				}
				
				if (title != null) {
					OmdbMovie omdbMovie = omdbApiClient.getOmdbMovie(title, "movie", year);
					numberOfOmdbApiCalls++;
					System.out.println("Number of OmdbApiCalls:" + numberOfOmdbApiCalls);
					//Write to the DB
					Movie movie = new Movie(titleAndYear, title, year, Long.valueOf(numberOfRatings),
							Float.valueOf(averageRating), ratingsDistribution,
							omdbMovie);
					System.out.println("Saving movie with the titleAndYear:" + titleAndYear + " to the DB. ImdbId=" + movie.getImdbId());
					try {
						movieService.createMovie(movie);
					} catch (Exception e) {
						e.printStackTrace();
						errorLogWriter.writeNext(new String[] {line});
					}
					
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

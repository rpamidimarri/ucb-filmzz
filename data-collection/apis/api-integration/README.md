# What does this module do?
This module has all the code to get data from IMDB, TMDB, OMDB, parse the data, store in the Postgres DB.

# How do you run this?
1. Make sure java is installed - Execute 'java' to see if it is.
2. Make sure maven is installed. 
a) First download maven from https://maven.apache.org/download.cgi (Download the binary zip file). 
b) Install maven with these simple 2 steps - https://maven.apache.org/install.html 
3. After maven is installed, execute 'mvn package' in the api-integartion directory. The project will be compiled and ready to run.
4. To get the latest data from TMDB about upcoming and running movies, run this command-
java -cp target/api-integration-0.0.1-SNAPSHOT.jar com.filmzz.tmdb.TmdbApiClient
5. Go to the Postgres DB and make a Select query on the ActiveMovie table. You should see hundreds of rows.
6. To parse the data from IMDB, you would first need the IMDB data files cleaned and ready to be parsed. Run this command to store all the IMDB data into the table Movie
java -cp target/api-integration-0.0.1-SNAPSHOT.jar com.filmzz.tmdb.ImdbFileReader <path_to_an_imdb_movie_ratings_file>
This will not only store those records in the DB, it will also generate 3 files (so that if something fails, you know how much this program could process so far).
 a) aggregated-csv with all the aggregated data
 b) non-aggregated-csv with the entries for records in the IMDB file for which we could not get an IMDBID (and hence no OMDB/Rotten Tomatoes ratings).
 c) error-log this will have entries for records in the IMDB file which generated errors while processing.
 



1. First create a database known as Filmzz
"Create Database Filmzz"
2. Go to the database Filmzz - "\c Filmzz"
3. Create the tables by running the scripts in this directory - should create the 4 tables.
4. NOTE - The Tweets and TweetsStatistic tables may have to be different - Arun can you please finalize these 2 table definitions based on what you are parsing out of the Tweets?


# Additional description about the Movie and ActiveMovie tables.

# Movie
This table corresponds to the entries in the IMDB file. 
An entry in IMDB file looks like
"0000000125  1637801   9.3  The Shawshank Redemption (1994)"
We only get a few fields (AND NO IMDBID) from the file.

We make a call to OMDB API with the title, year, to get the IMDBID and the metacritic, rottentomatoes ratings.
So, finally, a record in the Movie table will have the data from the IMDB file + Whatever OMDB returns for this movie.
(NOTE: It does not have TMDB ratings - TMDB does not have a good quality search API we can use).

# ActiveMovie
This table has all the currently running and upcoming movies. Every record in this table corresponds
to an entry in the output from TMDB API (Only TMDB API provides the list of currently running and upcoming movies).
We need to make another call to another TMDB API for every json we get from TMDB to get the associated IMDBID too.

So, finally, a record in the ActiveMovie will have the TMDB fields + IMDBID. 

If we need the metacritic, rottentomatoes, etc. ratings of a currently running, upcoming movie, we need to make a SQL query to get that from the Movie
table (if that record exists in Movie table) based on the imdbId.

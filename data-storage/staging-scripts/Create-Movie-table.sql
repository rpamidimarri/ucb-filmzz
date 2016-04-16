CREATE TABLE ActiveMovie (
tmdbId varchar(200),
imdbId varchar(64),
status varchar(20),
day varchar(64),
tmdbBudget varchar(200),
tmdbHomepage varchar(200),
tmdbOverview varchar(2000),
tmdbPopularity varchar(64),
tmdbReleaseDate varchar(64),
tmdbRevenue varchar(64),
tmdbRuntime varchar(64),
tmdbStatus varchar(64),
tmdbTagline varchar(100),
tmdbTitle varchar(200),
tmdbVoteAverage varchar(10),
tmdbVoteCount varchar(64),
tmdbVideo varchar(10),
CONSTRAINT tmdbId_day PRIMARY KEY(tmdbId,day)
)


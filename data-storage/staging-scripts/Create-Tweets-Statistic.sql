CREATE TABLE TweetStatistic (
tmdbId varchar(100),
keyword varchar(1000),
runningCount bigint,
runningPositiveSentiment real,
runningNegativeSentiment real,
CONSTRAINT tmdbId_stat PRIMARY KEY(tmdbId)
)

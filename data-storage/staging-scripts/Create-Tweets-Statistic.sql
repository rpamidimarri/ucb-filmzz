CREATE TABLE TweetStatistic (
imdbId varchar(64),
keyword varchar(200),
runningCount bigint,
runningPositiveSentiment real,
runningNegativeSentiment real,
CONSTRAINT imdbId_tweetTime_stat PRIMARY KEY(keyword)
)

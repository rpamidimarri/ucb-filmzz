DROP TABLE TweetStatistic;
CREATE TABLE TweetStatistic (
tmdbId varchar(100),
title varchar(1000),
runningCount bigint,
runningPositiveSentiment real,
runningNegativeSentiment real,
runningNeutralSentiment real,
runningCompoundSentiment real,
CONSTRAINT tmdbId_stat PRIMARY KEY(tmdbId)
)

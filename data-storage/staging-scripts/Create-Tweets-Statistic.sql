CREATE TABLE TweetStatistic (
imdbId varchar(64),
tweetTime varchar(64),
keyword varchar(200),
sentimentScore varchar(20),
CONSTRAINT imdbId_tweetTime_stat PRIMARY KEY(imdbId,tweetTime)
)

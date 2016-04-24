DROP TABLE Tweets;
CREATE TABLE Tweets (
id serial primary key,
tmdbId varchar(100),
tweetTime varchar(100),
keyword varchar(1000),
fullTweet varchar(1000),
latitude varchar(100),
longitude varchar(100),
positiveSentiment real,
negativeSentiment real,
neutralSentiment real,
compoundingSentiment real
);

-- Every15MinutesTweetMetricRun
---- Every 15 minutes 
delete from f_filmzz_metrics where tweetCount is not null and vote_day_wid = (select row_wid from d_day where date_dt = current_date);
  
insert into f_filmzz_metrics (vote_day_wid, movie_wid, tweetCount, totalPositiveSentiment, totalNegativeSentiment, totalNeutralSentiment, totalCompoundSentiment, insert_dt, update_dt)
  select 
    (select row_wid from d_day where date_dt = current_date),
    d_movie.row_wid,
    TweetStatistic.runningCount,
    TweetStatistic.runningPositiveSentiment,
    TweetStatistic.runningNegativeSentiment,
    TweetStatistic.runningNeutralSentiment,
    TweetStatistic.runningCompoundSentiment,
  now(),now()
  from TweetStatistic, d_movie where d_movie.tmdbid=TweetStatistic.tmdbid;

commit;

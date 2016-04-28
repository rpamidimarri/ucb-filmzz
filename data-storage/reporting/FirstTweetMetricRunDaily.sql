-- FirstTweetMetricRunDaily
--- First run each day just after midnight
-- Make sure current day is in d_day table
insert into d_day 
 (date_dt, weekday, weekday_name, year, year_name, quarter, quarter_name, month, month_name,
 week, week_name, day_of_year, day_of_year_name, day_of_month, day_of_month_name,
 is_yesterday, is_today, is_last_7_days, insert_dt,  update_dt)
 select current_date, cast(to_char(current_date, 'D') as int), to_char(current_date, 'day'),
             cast(to_char(current_date, 'YYYY') as int), to_char(current_date, 'YYYY'),
             cast(to_char(current_date,'Q') as int), to_char(current_date, 'YYYY')||' Q'||to_char(current_date, 'Q'),
             cast(to_char(current_date,'MM') as int), to_char(current_date,'Month'),
             cast(to_char(current_date,'WW') as int), to_char(current_date, 'YYYY')||' Week '||to_char(current_date, 'WW'),
             cast(to_char(current_date, 'DDD') as int), 'Day of Year:  '||to_char(current_date, 'DDD'),
             cast(to_char(current_date, 'DD') as int), to_char(current_date, 'Month')||' Day '||to_char(current_date, 'DD'),
             'Y', 'Y', 'Y', now(), now();

-- insert remainder of tweets for yesterday
delete from f_filmzz_metrics where tweetCount is not null and vote_day_wid = (select row_wid from d_day where date_dt = current_date-1);
  
insert into f_filmzz_metrics (vote_day_wid, movie_wid, tweetCount, totalPositiveSentiment, totalNegativeSentiment, totalNeutralSentiment, totalCompoundSentiment, insert_dt, update_dt)
  select 
    (select row_wid from d_day where date_dt = current_date-1),
    d_movie.row_wid,
    TweetStatistic.runningCount,
    TweetStatistic.runningPositiveSentiment,
    TweetStatistic.runningNegativeSentiment,
    TweetStatistic.runningNeutralSentiment,
    TweetStatistic.runningCompoundSentiment,
  now(),now()
  from TweetStatistic, d_movie where d_movie.tmdbid=TweetStatistic.tmdbid;
  
-- start over in TweetStatistic
delete from TweetStatistic;

commit;


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

drop table last_active_movie;

create table last_active_movie as
  select tmdbid, max(executioncount) executioncount
   from activemovie
   group by tmdbid;

drop table last_active_movie2;

create table last_active_movie2 as
  select a.* from activemovie a, last_active_movie b
    where a.tmdbid=b.tmdbid and a.executioncount=b.executioncount;

insert into d_movie
  (tmdbId,
  imdbId,
  status,
  tmdbBudget,
  tmdbHomepage,
  tmdbOverview,
  tmdbPopularity,
  tmdbReleaseDate,
  tmdbRevenue,
  tmdbRuntime,
  tmdbStatus,
  tmdbTagline,
  tmdbTitle,
  tmdbVideo,
  titleandyear,
  imdbtitle,
  imdbyear,
  imdbdistribution,
  omdbrated,
  omdbreleased,
  omdbruntime,
  omdbgenre,
  omdbdirector,
  omdbwriter,
  omdbactors,
  omdbplot,
  omdblanguage,
  omdbcountry,
  omdbawards,
  omdbposter,
  omdbmetascore,
  omdbtype,
  omdbdvd,
  omdbboxoffice,
  omdbproduction,
  omdbwebsite,
  tomatometer,
  tomatoimage,
  tomatorating,
  tomatoreviews,
  tomatofresh,
  tomatorotten,
  tomatoconsensus,
  tomatousermeter,
  tomatouserrating,
  tomatouserreviews,
  tomatourl,  
  insert_dt,
  update_dt)
select
  a.tmdbId,
  b.imdbId,
  a.status,
  a.tmdbBudget,
  a.tmdbHomepage,
  a.tmdbOverview,
  a.tmdbPopularity,
  a.tmdbReleaseDate,
  a.tmdbRevenue,
  a.tmdbRuntime,
  a.tmdbStatus,
  a.tmdbTagline,
  a.tmdbTitle,
  a.tmdbVideo,
  coalesce(b.titleandyear,tmdbTitle||' ('||substring(a.tmdbReleaseDate,1,4)||')'),
  b.imdbtitle,
  b.imdbyear,
  b.imdbdistribution,
  b.omdbrated,
  b.omdbreleased,
  b.omdbruntime,
  b.omdbgenre,
  b.omdbdirector,
  b.omdbwriter,
  b.omdbactors,
  b.omdbplot,
  b.omdblanguage,
  b.omdbcountry,
  b.omdbawards,
  b.omdbposter,
  b.omdbmetascore,
  b.omdbtype,
  b.omdbdvd,
  b.omdbboxoffice,
  b.omdbproduction,
  b.omdbwebsite,
  b.tomatometer,
  b.tomatoimage,
  b.tomatorating,
  b.tomatoreviews,
  b.tomatofresh,
  b.tomatorotten,
  b.tomatoconsensus,
  b.tomatousermeter,
  b.tomatouserrating,
  b.tomatouserreviews,
  b.tomatourl,  
  NOW(),
  NOW()
  from last_active_movie2 a
  left join movie b on a.imdbid=b.imdbid
  where a.tmdbId not in (select tmdbId from d_movie);


drop table this_tmdb_metrics;

-- insert current counts
create table this_tmdb_metrics as
  select
    (select row_wid from d_day where date_dt = current_date) vote_day_wid ,
    d_movie.row_wid movie_wid,
    cast(last_active_movie2.tmdbvotecount as bigint) tmdbVoteCount,
    cast(last_active_movie2.tmdbvoteaverage as real)*cast(last_active_movie2.tmdbvotecount as bigint) tmdbVoteTotal
    from last_active_movie2, d_movie where d_movie.tmdbid=last_active_movie2.tmdbid;

-- subtract off counts from yesterday
insert into this_tmdb_metrics
  select (select row_wid from d_day where date_dt = current_date) vote_day_wid, movie_wid, -tmdbVoteCount, -tmdbVoteTotal
  from previous_tmdb_metrics;


insert into f_filmzz_metrics (vote_day_wid, movie_wid, tmdbVoteCount, tmdbVoteTotal, insert_dt, update_dt)
  select vote_day_wid, movie_wid, sum(tmdbVoteCount), sum(tmdbVoteTotal), now(), now()
  from this_tmdb_metrics
  group by vote_day_wid, movie_wid having sum(tmdbVoteCount)<>0;
  
drop table previous_tmdb_metrics;

create table previous_tmdb_metrics as
  select
    (select row_wid from d_day where date_dt = current_date) vote_day_wid ,
    d_movie.row_wid movie_wid,
    cast(last_active_movie2.tmdbvotecount as bigint) tmdbVoteCount,
    cast(last_active_movie2.tmdbvoteaverage as real)*cast(last_active_movie2.tmdbvotecount as bigint) tmdbVoteTotal
    from last_active_movie2, d_movie where d_movie.tmdbid=last_active_movie2.tmdbid;

    
commit;





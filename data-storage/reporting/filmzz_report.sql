
drop table f_filmzz_metrics;

create table f_filmzz_metrics
(
  vote_day_wid int references d_day(row_wid),
  movie_wid int references d_movie(row_wid),

  tmdbVoteCount  int,
  tmdbVoteTotal  real,                      -- sum for the day instead of average,
  --tmdbPopularity numeric(22,7) not null,          -- figure out Popularity this out later
  
  tweetCount bigint,
  totalPositiveSentiment real,             -- sum for the day instead of average,
  totalNegativeSentiment real,             -- sum for the day instead of average,
  totalNeutralSentiment  real,             -- sum for the day instead of average,
  totalCompoundSentiment real,             -- sum for the day instead of average,

  imdbvotes       int,                              -- leaving out imdb for now since it isn't daily
  imdbTotalRating real,                             -- leaving out imdb for now since it isn't daily
  
  insert_dt date,
  update_dt date
);




create sequence seq_day;

create table d_day
(
  row_wid integer default nextval('seq_day'),
  date_dt date not null,
  weekday integer not null,
  weekday_name varchar(80) not null,
  year integer not null,
  year_name varchar(80) not null,
  quarter integer not null,
  quarter_name varchar(80) not null,
  month integer not null,
  month_name varchar(80) not null,
  week integer not null,
  week_name varchar(80) not null,
  day_of_year integer not null,
  day_of_year_name varchar(80) not null,
  day_of_month integer not null,
  day_of_month_name varchar(80) not null,
  is_yesterday varchar(1) not null,
  is_today varchar(1) not null,
  is_last_7_days varchar(1) not null,
  insert_dt date,
  update_dt date,  
  CONSTRAINT d_day_pk PRIMARY KEY(row_wid)  
);

create sequence seq_movie;
  
drop table d_movie;

create table d_movie 
(
  row_wid integer default nextval('seq_movie'),
  tmdbId varchar(100),
  imdbId varchar(100),
  status varchar(100),
  tmdbBudget varchar(200),
  tmdbHomepage varchar(200),
  tmdbOverview varchar(2000),
  tmdbPopularity varchar(64),
  tmdbReleaseDate varchar(64),
  tmdbRevenue varchar(64),
  tmdbRuntime varchar(64),
  tmdbStatus varchar(64),
  tmdbTagline varchar(1000),
  tmdbTitle varchar(1000),
  tmdbVideo varchar(10),
  titleandyear character varying(200) NOT NULL,
  imdbtitle character varying(200),
  imdbyear character varying(100),
  imdbdistribution character varying(20),
  omdbrated character varying(100),
  omdbreleased character varying(100),
  omdbruntime character varying(100),
  omdbgenre character varying(1000),
  omdbdirector character varying(1000),
  omdbwriter character varying(1000),
  omdbactors character varying(2000),
  omdbplot character varying(2000),
  omdblanguage character varying(200),
  omdbcountry character varying(200),
  omdbawards character varying(2000),
  omdbposter character varying(200),
  omdbmetascore character varying(100),
  omdbtype character varying(100),
  omdbdvd character varying(200),
  omdbboxoffice character varying(200),
  omdbproduction character varying(1000),
  omdbwebsite character varying(200),
  tomatometer character varying(100),
  tomatoimage character varying(100),
  tomatorating character varying(100),
  tomatoreviews character varying(100),
  tomatofresh character varying(100),
  tomatorotten character varying(100),
  tomatoconsensus character varying(1000),
  tomatousermeter character varying(100),
  tomatouserrating character varying(100),
  tomatouserreviews character varying(1000),
  tomatourl character varying(200),  
  insert_dt date,
  update_dt date,
  CONSTRAINT d_movie_pk PRIMARY KEY(row_wid)
);

create view reportview_filmzz as
select
 d.date_dt, d.weekday, d.weekday_name, d.year, d.year_name, d.quarter, d.quarter_name, d.month, d.month_name,
 d.week, d.week_name, d.day_of_year, d.day_of_year_name, d.day_of_month, d.day_of_month_name,
 d.is_yesterday, d.is_today, d.is_last_7_days,
  m.tmdbId,
  m.imdbId,
  m.status,
  m.tmdbBudget,
  m.tmdbHomepage,
  m.tmdbOverview,
  m.tmdbPopularity,
  m.tmdbReleaseDate,
  m.tmdbRevenue,
  m.tmdbRuntime,
  m.tmdbStatus,
  m.tmdbTagline,
  m.tmdbTitle,
  m.tmdbVideo,
  m.titleandyear,
  m.imdbtitle,
  m.imdbyear,
  m.imdbdistribution,
  m.omdbrated,
  m.omdbreleased,
  m.omdbruntime,
  m.omdbgenre,
  m.omdbdirector,
  m.omdbwriter,
  m.omdbactors,
  m.omdbplot,
  m.omdblanguage,
  m.omdbcountry,
  m.omdbawards,
  m.omdbposter,
  m.omdbmetascore,
  m.omdbtype,
  m.omdbdvd,
  m.omdbboxoffice,
  m.omdbproduction,
  m.omdbwebsite,
  m.tomatometer,
  m.tomatoimage,
  m.tomatorating,
  m.tomatoreviews,
  m.tomatofresh,
  m.tomatorotten,
  m.tomatoconsensus,
  m.tomatousermeter,
  m.tomatouserrating,
  m.tomatouserreviews,
  m.tomatourl,
 f.tmdbVoteCount, f.tmdbVoteTotal, f.tweetCount, f.totalPositiveSentiment, f.totalNegativeSentiment, f.totalNeutralSentiment, f.totalCompoundSentiment
 from f_filmzz_metrics f
 inner join d_day d on d.row_wid=f.vote_day_wid
 inner join d_movie m on m.row_wid=f.movie_wid;
 
 
CREATE TABLE activemovie
(
  tmdbid character varying(100) NOT NULL,
  imdbid character varying(100),
  status character varying(100),
  title character varying(1000),
  executiontime character varying(100),
  executioncount bigint NOT NULL,
  tmdbbudget character varying(200),
  tmdbhomepage character varying(200),
  tmdboverview character varying(2000),
  tmdbpopularity character varying(64),
  tmdbreleasedate character varying(64),
  tmdbrevenue character varying(64),
  tmdbruntime character varying(64),
  tmdbstatus character varying(64),
  tmdbtagline character varying(1000),
  tmdbtitle character varying(1000),
  tmdbvoteaverage character varying(100),
  tmdbvotecount character varying(64),
  tmdbvideo character varying(10),
  CONSTRAINT tmdbid_count PRIMARY KEY (tmdbid, executioncount)
)

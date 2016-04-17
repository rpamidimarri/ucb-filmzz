package com.filmzz.tmdb.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.filmzz.tmdb.model.OmdbMovie;

@Entity
@Table(name = "Movie")
public class Movie implements Serializable {
	//All imdb fields from the file
	@Id
	private String titleAndYear;
	private String imdbTitle;
	private String imdbYear;
	private long imdbVotes;
	private float imdbavgrating;
	private String imdbDistribution;
	
	private String imdbId;
	
	//All omdb fields
	private String omdbRated;
	private String omdbReleased;
	private String omdbRuntime;
	private String omdbGenre;
	private String omdbDirector;
	private String omdbWriter;
	private String omdbActors;
	private String omdbPlot;
	private String omdbLanguage;
	private String omdbCountry;
	private String omdbAwards;
	private String omdbPoster;
	private String omdbMetaScore;
	private String omdbType;
	private String omdbDvd;
	private String omdbBoxOffice;
	private String omdbWebsite;
	private String omdbProduction;
	//All Omdb - Rotten tomatoes fields
	private String tomatoMeter;
	private String tomatoImage;
	private String tomatoRating;
	private String tomatoReviews;
	private String tomatoFresh;
	private String tomatoRotten;
	private String tomatoConsensus;
	private String tomatoUserMeter;
	private String tomatoUserRating;
	private String tomatoUserReviews;
	private String tomatoUrl;
	
	public Movie() {
		
	}
	
	public Movie(String titleAndYear, String imdbTitle, String imdbYear,
			long imdbVotes, float imdbavgrating, String imdbDistribution,
			OmdbMovie omdbMovie) {
		this.imdbTitle = imdbTitle;
		this.titleAndYear = titleAndYear;
		this.imdbYear = imdbYear;
		this.imdbVotes = imdbVotes;
		this.imdbavgrating = imdbavgrating;
		this.imdbDistribution = imdbDistribution;
		
		this.imdbId = omdbMovie.getImdbID();
		
		//All other omdb fields
		this.omdbRated = omdbMovie.getRated();
		this.omdbReleased = omdbMovie.getReleased();
		this.omdbRuntime = omdbMovie.getRuntime();
		this.omdbGenre = omdbMovie.getGenre();
		this.omdbDirector = omdbMovie.getDirector();
		this.omdbWriter = omdbMovie.getWriter();
		this.omdbActors = omdbMovie.getActors();
		this.omdbPlot = omdbMovie.getPlot();
		this.omdbLanguage = omdbMovie.getLanguage();
		this.omdbCountry = omdbMovie.getCountry();
		this.omdbAwards= omdbMovie.getAwards();
		this.omdbPoster = omdbMovie.getPoster();
		this.omdbMetaScore = omdbMovie.getMetascore();
		this.omdbType = omdbMovie.getType();
		this.omdbDvd = omdbMovie.getdVD();
		this.omdbBoxOffice = omdbMovie.getBoxOffice();
		this.omdbWebsite = omdbMovie.getWebsite();
		this.omdbProduction = omdbMovie.getProduction();
		
		//Rotten tomatoes
		this.tomatoMeter = omdbMovie.getTomatoMeter();
		this.tomatoImage = omdbMovie.getTomatoImage();
		this.tomatoRating = omdbMovie.getTomatoRating();
		this.tomatoReviews = omdbMovie.getTomatoReviews();
		this.tomatoFresh = omdbMovie.getTomatoFresh();
		this.tomatoRotten = omdbMovie.getTomatoRotten();
		this.tomatoConsensus = omdbMovie.getTomatoConsensus();
		this.tomatoUserMeter = omdbMovie.getTomatoUserMeter();
		this.tomatoUserRating = omdbMovie.getTomatoUserRating();
		this.tomatoUserReviews = omdbMovie.getTomatoUserReviews();
		this.tomatoUrl = omdbMovie.getTomatoURL();
	}
	
	public String getTitleAndYear() {
		return titleAndYear;
	}
	public void setTitleAndYear(String titleAndYear) {
		this.titleAndYear = titleAndYear;
	}
	public String getImdbTitle() {
		return imdbTitle;
	}
	public void setImdbTitle(String imdbTitle) {
		this.imdbTitle = imdbTitle;
	}
	
	
	
	public long getImdbVotes() {
		return imdbVotes;
	}

	public void setImdbVotes(long imdbVotes) {
		this.imdbVotes = imdbVotes;
	}

	public float getImdbavgrating() {
		return imdbavgrating;
	}

	public void setImdbavgrating(float imdbavgrating) {
		this.imdbavgrating = imdbavgrating;
	}

	public String getImdbDistribution() {
		return imdbDistribution;
	}
	public void setImdbDistribution(String imdbDistribution) {
		this.imdbDistribution = imdbDistribution;
	}
	public String getImdbId() {
		return imdbId;
	}
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}
	public String getOmdbRated() {
		return omdbRated;
	}
	public void setOmdbRated(String omdbRated) {
		this.omdbRated = omdbRated;
	}
	public String getOmdbReleased() {
		return omdbReleased;
	}
	public void setOmdbReleased(String omdbReleased) {
		this.omdbReleased = omdbReleased;
	}
	public String getOmdbRuntime() {
		return omdbRuntime;
	}
	public void setOmdbRuntime(String omdbRuntime) {
		this.omdbRuntime = omdbRuntime;
	}
	public String getOmdbGenre() {
		return omdbGenre;
	}
	public void setOmdbGenre(String omdbGenre) {
		this.omdbGenre = omdbGenre;
	}
	public String getOmdbDirector() {
		return omdbDirector;
	}
	public void setOmdbDirector(String omdbDirector) {
		this.omdbDirector = omdbDirector;
	}
	public String getOmdbWriter() {
		return omdbWriter;
	}
	public void setOmdbWriter(String omdbWriter) {
		this.omdbWriter = omdbWriter;
	}
	public String getOmdbActors() {
		return omdbActors;
	}
	public void setOmdbActors(String omdbActors) {
		this.omdbActors = omdbActors;
	}
	public String getOmdbPlot() {
		return omdbPlot;
	}
	public void setOmdbPlot(String omdbPlot) {
		this.omdbPlot = omdbPlot;
	}
	public String getOmdbLanguage() {
		return omdbLanguage;
	}
	public void setOmdbLanguage(String omdbLanguage) {
		this.omdbLanguage = omdbLanguage;
	}
	public String getOmdbCountry() {
		return omdbCountry;
	}
	public void setOmdbCountry(String omdbCountry) {
		this.omdbCountry = omdbCountry;
	}
	public String getOmdbAwards() {
		return omdbAwards;
	}
	public void setOmdbAwards(String omdbAwards) {
		this.omdbAwards = omdbAwards;
	}
	public String getOmdbPoster() {
		return omdbPoster;
	}
	public void setOmdbPoster(String omdbPoster) {
		this.omdbPoster = omdbPoster;
	}
	public String getOmdbMetaScore() {
		return omdbMetaScore;
	}
	public void setOmdbMetaScore(String omdbMetaScore) {
		this.omdbMetaScore = omdbMetaScore;
	}
	public String getOmdbType() {
		return omdbType;
	}
	public void setOmdbType(String omdbType) {
		this.omdbType = omdbType;
	}
	public String getOmdbDvd() {
		return omdbDvd;
	}
	public void setOmdbDvd(String omdbDvd) {
		this.omdbDvd = omdbDvd;
	}
	public String getOmdbBoxOffice() {
		return omdbBoxOffice;
	}
	public void setOmdbBoxOffice(String omdbBoxOffice) {
		this.omdbBoxOffice = omdbBoxOffice;
	}
	public String getOmdbWebsite() {
		return omdbWebsite;
	}
	public void setOmdbWebsite(String omdbWebsite) {
		this.omdbWebsite = omdbWebsite;
	}
	public String getOmdbProduction() {
		return omdbProduction;
	}
	public void setOmdbProduction(String omdbProduction) {
		this.omdbProduction = omdbProduction;
	}
	public String getTomatoMeter() {
		return tomatoMeter;
	}
	public void setTomatoMeter(String tomatoMeter) {
		this.tomatoMeter = tomatoMeter;
	}
	public String getTomatoImage() {
		return tomatoImage;
	}
	public void setTomatoImage(String tomatoImage) {
		this.tomatoImage = tomatoImage;
	}
	public String getTomatoRating() {
		return tomatoRating;
	}
	public void setTomatoRating(String tomatoRating) {
		this.tomatoRating = tomatoRating;
	}
	public String getTomatoReviews() {
		return tomatoReviews;
	}
	public void setTomatoReviews(String tomatoReviews) {
		this.tomatoReviews = tomatoReviews;
	}
	public String getTomatoFresh() {
		return tomatoFresh;
	}
	public void setTomatoFresh(String tomatoFresh) {
		this.tomatoFresh = tomatoFresh;
	}
	public String getTomatoRotten() {
		return tomatoRotten;
	}
	public void setTomatoRotten(String tomatoRotten) {
		this.tomatoRotten = tomatoRotten;
	}
	public String getTomatoConsensus() {
		return tomatoConsensus;
	}
	public void setTomatoConsensus(String tomatoConsensus) {
		this.tomatoConsensus = tomatoConsensus;
	}
	public String getTomatoUserMeter() {
		return tomatoUserMeter;
	}
	public void setTomatoUserMeter(String tomatoUserMeter) {
		this.tomatoUserMeter = tomatoUserMeter;
	}
	public String getTomatoUserRating() {
		return tomatoUserRating;
	}
	public void setTomatoUserRating(String tomatoUserRating) {
		this.tomatoUserRating = tomatoUserRating;
	}
	public String getTomatoUserReviews() {
		return tomatoUserReviews;
	}
	public void setTomatoUserReviews(String tomatoUserReviews) {
		this.tomatoUserReviews = tomatoUserReviews;
	}
	public String getTomatoUrl() {
		return tomatoUrl;
	}
	public void setTomatoUrl(String tomatoUrl) {
		this.tomatoUrl = tomatoUrl;
	}	
	public String getImdbYear() {
		return imdbYear;
	}

	public void setImdbYear(String imdbYear) {
		this.imdbYear = imdbYear;
	}
	
}

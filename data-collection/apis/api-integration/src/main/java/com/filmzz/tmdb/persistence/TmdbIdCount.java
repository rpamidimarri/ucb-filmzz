package com.filmzz.tmdb.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TmdbIdCount implements Serializable{
	private String tmdbId;
	private long executionCount;

	public TmdbIdCount() {

	}

	public String getTmdbId() {
		return tmdbId;
	}

	public void setTmdbId(String tmdbId) {
		this.tmdbId = tmdbId;
	}

	public long getExecutionCount() {
		return executionCount;
	}

	public void setExecutionCount(long executionCount) {
		this.executionCount = executionCount;
	}

	@Override
	public String toString() {
		return "TmdbIdCount [tmdbId=" + tmdbId + ", executionCount=" + executionCount + "]";
	}
}


package com.filmzz.tmdb.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductionCountry {
	private String isoCode;
	private String name;
	
	public ProductionCountry() {
		
	}
	
	public ProductionCountry(String isoCode, String name) {
		super();
		this.isoCode = isoCode;
		this.name = name;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ProductionCountry [isoCode=" + isoCode + ", name=" + name + "]";
	}
	
	
}

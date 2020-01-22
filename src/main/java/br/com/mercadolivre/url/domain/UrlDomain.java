package br.com.mercadolivre.url.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UrlDomain {
	private String url;

	@JsonCreator
	public UrlDomain() {

	}

	@JsonCreator
	public UrlDomain(@JsonProperty("url") String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}

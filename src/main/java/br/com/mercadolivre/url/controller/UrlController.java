package br.com.mercadolivre.url.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.url.domain.UrlDomain;
import br.com.mercadolivre.url.service.UrlService;
import br.com.mercadolivre.url.validation.UrlValidator;

@RestController
public class UrlController {

	private static final Logger LOG = LoggerFactory.getLogger(UrlController.class);
	
	private final UrlService urlService;

	public UrlController(UrlService urlService) {
	        this.urlService = urlService;
	}

	@PostMapping(value = "/shortener", consumes = { "application/json" })
	public ResponseEntity<UrlDomain> insertUrlLong(@RequestBody @Valid final UrlDomain urlDomain, HttpServletRequest request){
		
		String longUrl = urlDomain.getUrl();
		LOG.info("Getting url from body: " +  longUrl);
		UrlValidator.INSTANCE.validateURL(longUrl) ;
		LOG.info("Accessing UrlValidator...");
		String localURL = request.getRequestURL().toString();
		LOG.info("Getting local URL: " + localURL);
		UrlDomain shortenedUrl = new UrlDomain(urlService.shortenURL(localURL, urlDomain.getUrl()));
		LOG.info("Shortned URL: " + shortenedUrl);
		return ResponseEntity.ok(shortenedUrl);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UrlDomain> findUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception{
		LOG.info("Accessing UrlService...");
		UrlDomain shortenedUrl = new UrlDomain(urlService.getLongURLFromID(id));
		LOG.info("Long URL successfully obtained!");
		return ResponseEntity.ok(shortenedUrl);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteUrlShortner(@PathVariable String id) throws Exception {
		LOG.info("Accessing UrlService...");
		urlService.shortnerUrlDelete(id);
		LOG.info("Deleted URL Shortner successfully! " + "Id - " + id);
		return ResponseEntity.noContent().build(); 
		
	}
}




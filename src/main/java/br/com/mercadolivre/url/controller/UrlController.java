package br.com.mercadolivre.url.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

	private final UrlService urlService;

	public UrlController(UrlService urlService) {
	        this.urlService = urlService;
	    }

	@PostMapping(value = "/shortener", consumes = { "application/json" })
	public ResponseEntity<UrlDomain> insertUrlLong(@RequestBody @Valid final UrlDomain shortenRequest, HttpServletRequest request){
		String longUrl = shortenRequest.getUrl();
		UrlValidator.INSTANCE.validateURL(longUrl) ;
		
		String localURL = request.getRequestURL().toString();
		UrlDomain shortenedUrl = new UrlDomain(urlService.shortenURL(localURL, shortenRequest.getUrl()));
		
		return ResponseEntity.ok(shortenedUrl);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UrlDomain> findUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception{
		UrlDomain shortenedUrl = new UrlDomain(urlService.getLongURLFromID(id));
		return ResponseEntity.ok(shortenedUrl);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteUrlShortner(@PathVariable String id) throws Exception {
		urlService.shortnerUrlDelete(id);
		return ResponseEntity.noContent().build(); 
		
	}
}




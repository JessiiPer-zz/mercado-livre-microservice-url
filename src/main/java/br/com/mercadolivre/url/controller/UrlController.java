package br.com.mercadolivre.url.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.url.domain.UrlDomain;
import br.com.mercadolivre.url.service.UrlService;
import br.com.mercadolivre.url.service.exception.InvalidUrlException;
import br.com.mercadolivre.url.validation.UrlValidator;

@RestController
public class UrlController {

	private final UrlService urlService;

	public UrlController(UrlService urlService) {
	        this.urlService = urlService;
	    }

	@RequestMapping(value = "/shortener", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<UrlDomain> shortenUrl(@RequestBody @Valid final UrlDomain shortenRequest, HttpServletRequest request)
			throws Exception {
		String longUrl = shortenRequest.getUrl();
		if (UrlValidator.INSTANCE.validateURL(longUrl)) {
			String localURL = request.getRequestURL().toString();
			UrlDomain shortenedUrl = new UrlDomain(urlService.shortenURL(localURL, shortenRequest.getUrl()));
			return ResponseEntity.ok(shortenedUrl);
		}
		throw new InvalidUrlException("Please enter a valid URL");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UrlDomain> redirectUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException, URISyntaxException, Exception {
		UrlDomain shortenedUrl = new UrlDomain(urlService.getLongURLFromID(id));
//		RedirectView redirectView = new RedirectView();
//		redirectView.setUrl(redirectUrlString);
		return ResponseEntity.ok(shortenedUrl);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteUrlShortner(@PathVariable String id) throws Exception {
		urlService.shortnerUrlDelete(id);
		return ResponseEntity.noContent().build();
		
	}
}




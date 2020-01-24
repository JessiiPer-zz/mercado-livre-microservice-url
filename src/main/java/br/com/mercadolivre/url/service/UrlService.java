package br.com.mercadolivre.url.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mercadolivre.url.commons.IDConverter;
import br.com.mercadolivre.url.controller.UrlController;
import br.com.mercadolivre.url.repository.UrlRepository;
import br.com.mercadolivre.url.service.exception.ResourceNotFoundException;

@Service
public class UrlService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UrlController.class);
	
	private final UrlRepository urlRepository;

	@Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository; 
    }

	public String shortenURL(String localURL, String longUrl) {
		LOG.info("Incrementing ID to the repository...");
		Long id = urlRepository.incrementID();
		LOG.info("Incremented ID: " + id);
		LOG.info("Creating Unique ID...");
		String uniqueID = IDConverter.INSTANCE.createUniqueID(id);
		LOG.info("ID Unique Created: " + uniqueID);
		LOG.info("Saving URL to the repository...");
		urlRepository.saveUrl("url:" + id, longUrl);
		LOG.info("Url saved - " + longUrl);
		String baseString = formatLocalURLFromShortener(localURL);
		String shortenedURL = baseString + uniqueID;
		LOG.info("Shortened URL: " + shortenedURL);
		return shortenedURL;
	}

	public String getLongURLFromID(String uniqueID) throws Exception{
		LOG.info("Getting dictionary key from Unique ID: " + uniqueID);
		Long dictionaryKey = IDConverter.INSTANCE.getDictionaryKeyFromUniqueID(uniqueID);
		String longUrl;
		LOG.info("Getting long URL to the repository...");
		longUrl = urlRepository.getUrl(dictionaryKey);
		LOG.info("Get Long URL successfully!: " + longUrl);
		if (longUrl == null) {
			LOG.error("URL at key " + dictionaryKey + " was not found");
			throw new ResourceNotFoundException("URL at key " + dictionaryKey + " was not found");
		}else return longUrl;
	}

	private String formatLocalURLFromShortener(String localURL) {
        String[] addressComponents = localURL.split("/");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addressComponents.length - 1; ++i) {
            sb.append(addressComponents[i]);
        }
        String formatString = sb.substring(0,4) + "://" + sb.substring(5,sb.length()) + "/";
        return formatString;
    }
	
	public void shortnerUrlDelete(String uniqueID) throws Exception{
		Long dictionaryKey = IDConverter.INSTANCE.getDictionaryKeyFromUniqueID(uniqueID);
		LOG.info("Get dictionary key" + dictionaryKey);
		urlRepository.deleteUrl(dictionaryKey);
		LOG.info("Shortner URL deleted successfully!");
	}
}
package br.com.mercadolivre.url.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.mercadolivre.url.commons.IDConverter;
import br.com.mercadolivre.url.repository.UrlRepository;
import br.com.mercadolivre.url.service.exception.ResourceNotFoundException;

@Service
public class UrlService {
	
	private final UrlRepository urlRepository;

	@Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository; 
    }

	public String shortenURL(String localURL, String longUrl) {
		Long id = urlRepository.incrementID();
		String uniqueID = IDConverter.INSTANCE.createUniqueID(id);
		urlRepository.saveUrl("url:" + id, longUrl);
		String baseString = formatLocalURLFromShortener(localURL);
		String shortenedURL = baseString + uniqueID;
		return shortenedURL;
	}

	public String getLongURLFromID(String uniqueID) throws Exception{
		Long dictionaryKey = IDConverter.INSTANCE.getDictionaryKeyFromUniqueID(uniqueID);
		String longUrl;
		longUrl = urlRepository.getUrl(dictionaryKey);
		if (longUrl == null) {
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
		urlRepository.deleteUrl(dictionaryKey);
	}
}
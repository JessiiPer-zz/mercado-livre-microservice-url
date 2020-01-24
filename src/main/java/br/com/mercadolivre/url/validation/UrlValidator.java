package br.com.mercadolivre.url.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mercadolivre.url.service.exception.InvalidUrlException;

public class UrlValidator {
	
	private static final Logger LOG = LoggerFactory.getLogger(UrlValidator.class);
	
	public static final UrlValidator INSTANCE = new UrlValidator();
    private static final String URL_REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    private UrlValidator() {
    }

    public boolean validateURL(String url) {
        Matcher m = URL_PATTERN.matcher(url);
        if (!m.matches()) {
        	LOG.error("[validateURL] - Invalid url");
        	throw new InvalidUrlException("Please enter a valid URL");
        }
        return m.matches();
    }
}

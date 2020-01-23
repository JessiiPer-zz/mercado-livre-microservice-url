package br.com.mercadolivre.url.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.mercadolivre.url.service.exception.InvalidUrlException;
import br.com.mercadolivre.url.validation.UrlValidator;

@RunWith(MockitoJUnitRunner.class)
public class UrlValidatorTest {

	@InjectMocks
	private UrlValidator validator;
		
	String url_long_incorrect = "{\r\n" + 
			"    \"url\": \"htips://www.modalmais.com.br/\"\r\n" + 
			"}";
	
	String url_long_correct = "https://www.modalmais.com.br";
	
	@Test
	public void shortenUrlConverterValidTest(){
		assertThat(validator.validateURL(url_long_correct));
		
	}
	
	@Test(expected= InvalidUrlException.class)
	public void shortenUrlConverterInvalidTest() throws Exception {
		assertFalse(validator.validateURL(url_long_incorrect));
		
	}
	
}

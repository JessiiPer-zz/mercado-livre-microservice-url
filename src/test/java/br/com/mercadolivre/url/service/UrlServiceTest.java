package br.com.mercadolivre.url.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.mercadolivre.url.repository.UrlRepository;
import br.com.mercadolivre.url.service.exception.ResourceNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {

	@InjectMocks
	private UrlService urlService;
	
	@Mock
	private UrlRepository repository;
	
	String uniqueID = "a";

	String localURL = "http://localhost8080/shortener";

	String longURL = "https://www.modalmais.com.br";

	@SuppressWarnings("unused")
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.repository = mock(UrlRepository.class);
		@SuppressWarnings("unused")
		UrlService urlService = new UrlService(repository);
	}

	@Test
	public void shortenURLTest() {
		assertNotNull(urlService.shortenURL(localURL, longURL)); 
	}

	@Test
	public void getLongURLFromIDTest() throws Exception {

		UrlService service = new UrlService(repository);
		when(repository.getUrl(Mockito.anyLong())).thenReturn(longURL);
		
		assertNotNull(service.getLongURLFromID(uniqueID));
		verify(repository).getUrl(Mockito.anyLong());
	}
	
	@Test(expected=ResourceNotFoundException.class)
	public void getLongURLFromIDExceptionTest() throws Exception {

		UrlService service = new UrlService(repository);
		when(repository.getUrl(Mockito.anyLong())).thenReturn(null);
		
		assertNull(service.getLongURLFromID(uniqueID));
		verify(repository).getUrl(Mockito.anyLong());
	}
	
	@Test
	public void shortnerUrlDelete() throws Exception {
		
		UrlService service = new UrlService(repository);
		Mockito.doNothing().when(repository).deleteUrl(Mockito.anyLong());
		
		service.shortnerUrlDelete(uniqueID);
		verify(repository).deleteUrl(Mockito.anyLong());
	}
	
	
}

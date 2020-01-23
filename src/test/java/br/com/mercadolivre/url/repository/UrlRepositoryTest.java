package br.com.mercadolivre.url.repository;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import redis.clients.jedis.Jedis;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class UrlRepositoryTest {

	@Mock
	Jedis jedis;
	
	String idKey;
	
	String urlKey;
	
	UrlRepository repository = new UrlRepository();
	
	@Before
	public void setup() {
		 MockitoAnnotations.initMocks(this);
		 this.jedis = mock(Jedis.class);
		 @SuppressWarnings("unused")
		UrlRepository repository = new UrlRepository(jedis, idKey, urlKey);
		
	}
	
	@Test
	public void incrementIDTest() {
		UrlRepository repository = new UrlRepository();
		assertNotNull(repository.incrementID());
	}
	
	@Test
	public void saveUrlTest() {
		UrlRepository repository = new UrlRepository();
		repository.saveUrl("lalala", "teste");
	}
	
	@Test
	public void getUrlTest() {
		
		repository.getUrl(1l);
	}
	
	@Test
	public void deleteUrlTest() throws Exception {
		
		repository.deleteUrl(1l);
	}
}

package br.com.mercadolivre.url.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.mercadolivre.url.controller.exception.ResourceExceptionHandler;
import br.com.mercadolivre.url.domain.UrlDomain;
import br.com.mercadolivre.url.service.UrlService;
import br.com.mercadolivre.url.validation.UrlValidator;

@RunWith(MockitoJUnitRunner.class)
public class UrlControllerTest {
	
	@Mock
	private UrlValidator validator;
	
	@Mock
	private HttpServletRequest httpRequest;
	
	@Mock
	private UrlService service;
	
	private MvcResult result;
	
	private MockMvc mockMvc;
	
	
	@Before
	public void setup() {
		 MockitoAnnotations.initMocks(this);
		 this.service = mock(UrlService.class);
		 UrlController controller = new UrlController(service);
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ResourceExceptionHandler()).build();
		
	}
	
	String url_long_correct = "{\r\n" + 
			"    \"url\": \"https://www.modalmais.com.br/\"\r\n" + 
			"}";
	
	
	String url_short = "{\r\n" + 
			"    \"url\": \"https://localhost:8080/u\"\r\n" + 
			"            \r\n" + 
			"}"; 
	@Test
	public void insertUrlLongTest() throws Exception {
		
		when(service.shortenURL(Mockito.anyString(), Mockito.anyString())).thenReturn(domainShortnerMockValid().getUrl());
		
		result = this.mockMvc.perform(post("/shortener").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(domainUrlLongMockValid().getUrl())).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json(url_short))
				.andReturn();
		
		assertNotNull(result);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void findUrlShortnerTest() throws Exception {
		
		when(service.getLongURLFromID(Mockito.anyString())).thenReturn(domainShortnerMockValid().getUrl());
		
		result = this.mockMvc.perform(get("/9").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json(url_short))
				.andReturn();
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertTrue(result.getResponse().getContentAsString().contains("{\"url\":\"https://localhost:8080/u\"}"));
	}

	@Test
	public void deleteUrlShortner() throws Exception {
		doNothing().when(service).shortnerUrlDelete(Mockito.anyString());
		
		result = this.mockMvc.perform(delete("/9").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
				.andReturn();
		

		assertNotNull(result);
		assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
	}
	
	private UrlDomain domainShortnerMockValid() {
		UrlDomain domain = new UrlDomain("https://localhost:8080/u");
		domain.setUrl("https://localhost:8080/u");
		return domain;
	}
	
	private UrlDomain domainUrlLongMockValid() {
		UrlDomain domain = new UrlDomain();
		domain.setUrl("{\r\n" + 
				"    \"url\": \"https://www.modalmais.com.br/\"\r\n" + 
				"}");
		return domain;
	}
	
}

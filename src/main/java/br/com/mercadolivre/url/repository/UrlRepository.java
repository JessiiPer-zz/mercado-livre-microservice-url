package br.com.mercadolivre.url.repository;

import redis.clients.jedis.Jedis;
import org.springframework.stereotype.Repository;

import br.com.mercadolivre.url.service.exception.ResourceNotFoundException;

@Repository
public class UrlRepository {

	private final Jedis jedis;
	private final String idKey;
	private final String urlKey;

	public UrlRepository() {
		this.jedis = new Jedis();
		this.idKey = "id";
		this.urlKey = "url:";
	}

	public UrlRepository(Jedis jedis, String idKey, String urlKey) {
		this.jedis = jedis;
		this.idKey = idKey;
		this.urlKey = urlKey;
	}

	public Long incrementID() {
		Long id = jedis.incr(idKey);
		return id - 1;
	}

	public void saveUrl(String key, String longUrl) {
		jedis.hset(urlKey, key, longUrl);
	}

	public String getUrl(Long id) throws Exception {
		String url = jedis.hget(urlKey, "url:" + id);
		if (url == null) {
			throw new ResourceNotFoundException("URL at key " + id + " was not found");
		}
		return url;
	}
	
	public void deleteUrl(Long id) throws Exception {
        jedis.hdel(urlKey, "url:"+id);
        
	}

}

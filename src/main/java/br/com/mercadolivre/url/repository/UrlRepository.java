package br.com.mercadolivre.url.repository;

import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

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

	public String getUrl(Long id) {
		String url = jedis.hget(urlKey, "url:" + id);
		
		return url;
	}
	
	public void deleteUrl(Long id) throws Exception{
        jedis.hdel(urlKey, "url:"+id);
        
	}

}

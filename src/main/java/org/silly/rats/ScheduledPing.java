package org.silly.rats;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class ScheduledPing {
	@Scheduled(fixedDelay = 60000)
	public void ping() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/ping";
		restTemplate.getForEntity(url, String.class);
	}
}

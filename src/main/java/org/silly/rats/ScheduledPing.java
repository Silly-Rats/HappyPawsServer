package org.silly.rats;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ScheduledPing {
//	@Scheduled(fixedDelay = 60000)
//	public void ping() {
//		RestTemplate restTemplate = new RestTemplate();
//		restTemplate.getForEntity("https://happypawsserver.onrender.com/api/ping", String.class);
//		restTemplate.getForObject("https://happypaws-87hv.onrender.com/ping", String.class);
//	}
}

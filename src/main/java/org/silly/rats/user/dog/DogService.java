package org.silly.rats.user.dog;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DogService {
	private final DogRepository dogRepository;
	private final JwtService jwtService;

	public List<Dog> getUserDogs(String token) {
		token = token.substring(7);
		return dogRepository.getDogByUserId((Integer) jwtService.extractClaim(token, (c) -> c.get("id")));
	}
}

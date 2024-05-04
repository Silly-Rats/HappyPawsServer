package org.silly.rats.user.dog;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DogService {
	private final DogRepository dogRepository;

	public List<Dog> getUserDogs(Integer id) {
		return dogRepository.getDogByUserId(id);
	}
}

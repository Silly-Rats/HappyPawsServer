package org.silly.rats.user.dog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DogService {
	private final DogRepository dogRepository;

	public List<Dog> getUserDogs(Integer userId) {
		return dogRepository.getDogByUserId(userId);
	}
}

package org.silly.rats.user.dog;

import lombok.RequiredArgsConstructor;
import org.silly.rats.user.dog.breed.Breed;
import org.silly.rats.user.dog.breed.BreedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/dog")
@RequiredArgsConstructor
public class DogController {
	private final DogService dogService;
	private final BreedService breedService;

	@GetMapping(path = "/user")
	public List<Dog> getUserDogs(@RequestHeader(name = "Authorization") String token) {
		return dogService.getUserDogs(token);
	}

	@GetMapping("/breeds")
	public List<Breed> getAllBreeds() {
		return breedService.getAllBreeds();
	}
}

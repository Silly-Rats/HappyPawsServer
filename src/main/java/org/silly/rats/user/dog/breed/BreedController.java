package org.silly.rats.user.dog.breed;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/breed")
@RequiredArgsConstructor
public class BreedController {
	private final BreedService breedService;

	@GetMapping("")
	public List<Breed> getAllBreeds() {
		return breedService.getAllBreeds();
	}
}

package org.silly.rats.user.dog;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.silly.rats.user.dog.breed.Breed;
import org.silly.rats.user.dog.breed.BreedService;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping(path = "api/dog")
@RequiredArgsConstructor
public class DogController {
	private final DogService dogService;
	private final BreedService breedService;
	private final JwtService jwtService;

	@GetMapping(path = "/user")
	public List<Dog> getUserDogs(@RequestHeader(name = "Authorization") String token) {
		token = token.substring(7);
		Integer id = (Integer) jwtService.extractClaim(token, (c) -> c.get("id"));
		return dogService.getUserDogs(id);
	}

	@GetMapping("/breeds")
	public List<Breed> getAllBreeds() {
		return breedService.getAllBreeds();
	}

	@PostMapping(path = "/user")
	public void postDog(@RequestHeader(name = "Authorization") String token,
						@RequestBody DogRequest request)
			throws AuthenticationException {
		Integer id = extractId(token);
		dogService.saveDog(id, request);
	}

	@DeleteMapping(path = "/user/{dogId}")
	public void deleteDog(@RequestHeader(name = "Authorization") String token,
						  @PathVariable Integer dogId)
			throws AuthenticationException {
		Integer id = extractId(token);
		dogService.deleteDog(id, dogId);
	}

	private Integer extractId(String token) {
		token = token.substring(7);
		return (Integer) jwtService.extractClaim(token, (c) -> c.get("id"));
	}
}

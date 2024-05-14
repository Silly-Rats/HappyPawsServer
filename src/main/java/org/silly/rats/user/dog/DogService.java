package org.silly.rats.user.dog;

import lombok.RequiredArgsConstructor;
import org.silly.rats.user.User;
import org.silly.rats.user.UserRepository;
import org.silly.rats.user.dog.breed.Breed;
import org.silly.rats.user.dog.breed.BreedRepository;
import org.silly.rats.user.dog.breed.OtherBreed;
import org.silly.rats.user.dog.breed.OtherBreedRepository;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DogService {
	private final DogRepository dogRepository;
	private final BreedRepository breedRepository;
	private final OtherBreedRepository otherBreedRepository;
	private final UserRepository userRepository;

	public List<Dog> getUserDogs(Integer id) {
		return dogRepository.getDogByUserId(id);
	}

	public void deleteDog(Integer id, Integer dogId)
			throws AuthenticationException {
		User user = userRepository.findById(id).orElseThrow(() ->
				new RuntimeException("There is no user with id: " + id));
		if (!containDog(user, dogId)) {
			throw new AuthenticationException("User " + id + " don't have a dog with id: " + dogId);
		}

		dogRepository.deleteById(dogId);
	}

	private boolean containDog(User user, Integer dogId) {
		for (Dog dog : user.getDogs()) {
			if (dogId.equals(dog.getId())) {
				return true;
			}
		}
		return false;
	}

	public void saveDog(Integer id, DogRequest request)
			throws AuthenticationException {
		User user = userRepository.findById(id).orElseThrow(() ->
				new RuntimeException("There is no user with id: " + id));
		if (request.getId() != null && containDog(user, request.getId())) {
			throw new AuthenticationException("User " + id + " don't have a dog with id: " + request.getId());
		}

		Breed breed = null;
		if (request.getBreed() != null) {
			breed = breedRepository.findById(request.getBreed()).orElseThrow(() ->
					new IllegalArgumentException("There is no breed with id: " + request.getBreed()));
		}
		Dog dog = new Dog(null, request.getName(), request.getBirthday(),
				breed, null, request.getComment(), user, null);
		dog = dogRepository.save(dog);

		if (breed == null) {
			OtherBreed otherBreed = new OtherBreed(dog.getId(),
					request.getBreedName(), request.getSize(), null);
			otherBreedRepository.save(otherBreed);
		}
	}
}

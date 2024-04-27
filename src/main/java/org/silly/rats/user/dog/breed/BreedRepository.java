package org.silly.rats.user.dog.breed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedRepository
	extends JpaRepository<Breed, Integer> {
}

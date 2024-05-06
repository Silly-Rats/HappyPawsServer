package org.silly.rats.user.dog.breed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherBreedRepository
	extends JpaRepository<OtherBreed, Integer> {
}

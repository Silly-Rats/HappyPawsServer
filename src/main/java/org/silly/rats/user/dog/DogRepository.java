package org.silly.rats.user.dog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository
	extends JpaRepository<Dog, Integer> {
	List<Dog> getDogByUserId(Integer userId);
}

package org.silly.rats.reserve.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassRepository
	extends JpaRepository<Pass, Integer> {
	List<Pass> findByDogId(Integer dogId);
}

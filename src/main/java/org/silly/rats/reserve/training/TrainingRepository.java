package org.silly.rats.reserve.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrainingRepository
	extends JpaRepository<TrainingDetails, Long> {
	@Query("SELECT d FROM TrainingDetails d WHERE d.worker.id = ?1 " +
			"AND d.reserve.reserveTime BETWEEN ?2 AND ?3")
	List<TrainingDetails> findWorkerInterval(Integer workerId, LocalDateTime start, LocalDateTime end);


	@Query("SELECT d FROM TrainingDetails d WHERE d.reserve.dog.id = ?1")
	TrainingDetails findByUserId(Integer userId);
}

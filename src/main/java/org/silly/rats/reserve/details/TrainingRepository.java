package org.silly.rats.reserve.details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrainingRepository
	extends JpaRepository<TrainingDetails, Long> {
	@Query("SELECT d FROM TrainingDetails d WHERE d.reserve.dog.id = ?1")
	TrainingDetails findByDogId(Integer id);

	@Query("SELECT d FROM TrainingDetails d WHERE d.worker.id = ?1 AND d.reserveTime > ?2 AND d.reserveTime < ?3")
	List<TrainingDetails> findWorkerInterval(Integer worker_id, LocalDateTime start, LocalDateTime end);
}

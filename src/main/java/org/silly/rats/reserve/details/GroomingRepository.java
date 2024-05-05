package org.silly.rats.reserve.details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GroomingRepository
	extends JpaRepository<GroomingDetails, Long> {
	@Query("SELECT d FROM GroomingDetails d WHERE d.reserve.dog.id = ?1")
	GroomingDetails findByDogId(Integer id);

	@Query("SELECT d FROM GroomingDetails d WHERE d.worker.id = ?1 AND d.reserveTime BETWEEN ?2 AND ?3")
	List<GroomingDetails> findWorkerInterval(Integer worker_id, LocalDateTime start, LocalDateTime end);
}

package org.silly.rats.user.worker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepository
	extends JpaRepository<Worker, Integer> {
	@Query("SELECT w FROM Worker w WHERE w.user.type = ?1")
	List<Worker> findByType(Byte type);
}

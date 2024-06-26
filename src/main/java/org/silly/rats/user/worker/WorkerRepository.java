package org.silly.rats.user.worker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository
	extends JpaRepository<Worker, Integer> {

}

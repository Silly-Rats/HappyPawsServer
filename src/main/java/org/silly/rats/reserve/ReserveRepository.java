package org.silly.rats.reserve;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveRepository
		extends JpaRepository<Reserve, Long> {
	@Query("SELECT r FROM Reserve r WHERE r.dog.user.id = ?1")
	List<Reserve> findByUserId(Integer userId);
}

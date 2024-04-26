package org.silly.rats.reserve.details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HotelRepository
	extends JpaRepository<HotelDetails, Long> {
	@Query("SELECT d FROM HotelDetails d WHERE d.reserve.dog.id = ?1")
	TrainingDetails findByDogId(Integer id);

	@Query("SELECT d FROM HotelDetails d WHERE d.startDate >= ?1 AND d.endDate <= ?2")
	List<TrainingDetails> findBetweenDates(LocalDateTime start, LocalDateTime end);
}

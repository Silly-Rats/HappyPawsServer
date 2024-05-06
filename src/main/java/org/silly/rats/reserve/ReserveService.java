package org.silly.rats.reserve;

import lombok.RequiredArgsConstructor;
import org.silly.rats.reserve.grooming.GroomingRepository;
import org.silly.rats.reserve.hotel.HotelRepository;
import org.silly.rats.reserve.training.Pass;
import org.silly.rats.reserve.training.PassRepository;
import org.silly.rats.reserve.training.TrainingDetails;
import org.silly.rats.reserve.training.TrainingRepository;
import org.silly.rats.reserve.service.ServiceRepository;
import org.silly.rats.user.dog.Dog;
import org.silly.rats.user.dog.DogRepository;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReserveService {
	private final ServiceRepository serviceRepository;
	private final TrainingRepository trainingRepository;
	private final GroomingRepository groomingRepository;
	private final HotelRepository hotelRepository;
	private final PassRepository passRepository;
	private final DogRepository dogRepository;

	public Map<LocalDate, List<String>> getFreeTrainerHours(Integer worker, LocalDateTime start, LocalDateTime end) {
		List<TrainingDetails> details = trainingRepository.findWorkerInterval(worker, start, end.plusDays(1));
		Map<LocalDate, List<Integer>> busy = new HashMap<>();
		for (TrainingDetails detail : details) {
			busy.merge(detail.getReserveTime().toLocalDate(),
					new ArrayList<>(List.of(detail.getReserveTime().getHour())),
					(o, n) -> {
						o.addAll(n);
						return o;
					});
		}

		Map<LocalDate, List<String>> available = new HashMap<>();
		for (LocalDateTime date = start; date.isBefore(end); date = date.plusDays(1)) {
			for (int i = 9; i <= 18; i++) {
				addToMap(available, busy, date.toLocalDate(), i);
			}
		}

		int max = Math.min(end.getHour(), 18);
		for (int i = 9; i <= max; i++) {
			addToMap(available, busy, end.toLocalDate(), i);
		}

		return available;
	}

	public List<Pass> getDogPasses(Integer dogId, Integer userId)
			throws AuthenticationException {
		List<Dog> dogs = dogRepository.getDogByUserId(userId);
		if (dogId == null || !containsDog(dogs, dogId)) {
			throw new AuthenticationException("User don't have this dog");
		}

		return passRepository.findByDogId(dogId);
	}

	private void addToMap(Map<LocalDate, List<String>> available,
						  Map<LocalDate, List<Integer>> busy, LocalDate key, int i) {
		if (i == 13) {
			return;
		}
		if (!busy.containsKey(key) || !busy.get(key).contains(i)) {
			available.merge(key, new ArrayList<>(List.of("%02d:00".formatted(i))), (o, n) -> {
				o.addAll(n);
				return o;
			});
		}
	}

	private boolean containsDog(List<Dog> dogs, Integer dogId) {
		for (Dog d : dogs) {
			if (d.getId().equals(dogId)) {
				return true;
			}
		}

		return false;
	}
}

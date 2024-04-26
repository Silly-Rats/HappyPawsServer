package org.silly.rats.reserve;

import lombok.RequiredArgsConstructor;
import org.silly.rats.reserve.details.GroomingRepository;
import org.silly.rats.reserve.details.HotelRepository;
import org.silly.rats.reserve.details.TrainingDetails;
import org.silly.rats.reserve.details.TrainingRepository;
import org.silly.rats.reserve.service.ServiceRepository;
import org.springframework.stereotype.Service;

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
}

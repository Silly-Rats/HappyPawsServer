package org.silly.rats.reserve.training;

import lombok.RequiredArgsConstructor;
import org.silly.rats.reserve.Reserve;
import org.silly.rats.reserve.request.TrainingRequest;
import org.silly.rats.user.dog.Dog;
import org.silly.rats.user.worker.Worker;
import org.silly.rats.user.worker.WorkerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TrainingService {
	private final TrainingRepository trainingRepository;
	private final PassRepository passRepository;
	private final WorkerRepository workerRepository;

	public TrainingDetails getTrainingDetails(Long id) {
		return trainingRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("There is no training with id: " + id));
	}

	public Map<LocalDate, List<String>> getFreeHours(Integer worker, LocalDateTime start, LocalDateTime end) {
		List<TrainingDetails> details = trainingRepository.findWorkerInterval(worker, start, end.plusDays(1));
		Map<LocalDate, List<Integer>> busy = new HashMap<>();
		for (TrainingDetails detail : details) {
			busy.merge(detail.getReserve().getReserveTime().toLocalDate(),
					new ArrayList<>(List.of(detail.getReserve().getReserveTime().getHour())),
					(o, n) -> {
						o.addAll(n);
						return o;
					});
		}

		Map<LocalDate, List<String>> available = new HashMap<>();
		for (LocalDateTime date = start; date.isBefore(end); date = date.plusDays(1)) {
			for (int i = 9; i <= 18; i++) {
				if (i == 13) {
					continue;
				}
				addToMap(available, busy, date.toLocalDate(), i);
			}
		}

		int max = Math.min(end.getHour(), 18);
		for (int i = 9; i <= max; i++) {
			addToMap(available, busy, end.toLocalDate(), i);
		}

		return available;
	}

	public Pass getPass(Integer id) {
		if (id == null) {
			return null;
		}
		return passRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("No pass found with id: " + id));
	}

	public List<TrainingDetails> getTrainingReserves(Integer id, LocalDate date) {
		LocalDateTime start = date.atStartOfDay();
		LocalDateTime end = date.plusDays(1).atStartOfDay();
		return trainingRepository.findWorkerInterval(id, start, end);
	}

	public List<Pass> getDogPasses(Integer dogId) {
		return passRepository.findByDogId(dogId);
	}

	public void createReserves(List<Long> ids, Dog dog, TrainingRequest request) {
		Pass pass = null;
		Worker trainer = getTrainer(request.getTrainerId());

		if (request.getPassId() != null) {
			pass = getPass(request.getPassId());
		} else {
			if (request.getNeedPass()) {
				pass = passRepository.save(new Pass(null, dog,
						trainer.getUser(), null, false));
			}
		}

		for (Long id : ids) {
			TrainingDetails trainingDetails = new TrainingDetails(id, null, pass, trainer.getUser());
			trainingRepository.save(trainingDetails);
		}
	}

	private Worker getTrainer(Integer workerId) {
		Worker worker = workerRepository.findById(workerId).orElse(null);
		if (worker == null || !worker.getUser().getType().equals("trainer")) {
			throw new IllegalArgumentException("There is no trainer with id: " + workerId);
		}
		return worker;
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

	public Reserve getReserve(Long id, Pass pass) {
		TrainingDetails training = trainingRepository.findById(id)
				.orElseThrow(() ->
						new IllegalArgumentException("There is no training reserve with id: " + id));

		if (!pass.getTrainings().contains(training)) {
			throw new IllegalArgumentException("Pass don't contains reserve with id: " + id);
		}

		return training.getReserve();
	}
}

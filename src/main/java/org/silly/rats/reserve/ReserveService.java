package org.silly.rats.reserve;

import lombok.RequiredArgsConstructor;
import org.silly.rats.reserve.grooming.GroomingRepository;
import org.silly.rats.reserve.hotel.HotelRepository;
import org.silly.rats.reserve.request.PassPatchRequest;
import org.silly.rats.reserve.request.ReserveRequest;
import org.silly.rats.reserve.request.TrainingRequest;
import org.silly.rats.reserve.training.*;
import org.silly.rats.reserve.service.ServiceRepository;
import org.silly.rats.user.User;
import org.silly.rats.user.UserRepository;
import org.silly.rats.user.dog.Dog;
import org.silly.rats.user.dog.DogRepository;
import org.silly.rats.user.dog.breed.Breed;
import org.silly.rats.user.dog.breed.BreedRepository;
import org.silly.rats.user.dog.breed.OtherBreed;
import org.silly.rats.user.dog.breed.OtherBreedRepository;
import org.silly.rats.user.type.AccountTypeRepository;
import org.silly.rats.user.worker.Worker;
import org.silly.rats.user.worker.WorkerRepository;
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
	private final ReserveRepository reserveRepository;
	private final TrainingRepository trainingRepository;
	private final GroomingRepository groomingRepository;
	private final HotelRepository hotelRepository;
	private final PassRepository passRepository;

	private final DogRepository dogRepository;
	private final BreedRepository breedRepository;
	private final OtherBreedRepository otherBreedRepository;

	private final UserRepository userRepository;
	private final WorkerRepository workerRepository;
	private final AccountTypeRepository accountTypeRepository;

	public Map<LocalDate, List<String>> getFreeTrainerHours(Integer worker, LocalDateTime start, LocalDateTime end) {
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

	public List<Pass> getDogPasses(Integer dogId, Integer userId)
			throws AuthenticationException {
		Dog dog = getUserDog(dogId, userId);

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

	private Dog getDog(ReserveRequest request, Integer userId)
			throws Exception {
		if (request.getDogId() == null) {
			throw new RuntimeException("Dog Id must be specified");
		}
		return getUserDog(request.getDogId(), userId);
	}

	private Dog getUserDog(Integer dogId, Integer userId)
			throws AuthenticationException {
		List<Dog> dogs = dogRepository.getDogByUserId(userId);
		Dog dog = getDogFromList(dogs, dogId);
		if (dog == null) {
			throw new AuthenticationException("User don't have this dog: id =" + dogId);
		}
		return dog;
	}

	private Dog getDogFromList(List<Dog> dogs, Integer dogId) {
		for (Dog d : dogs) {
			if (d.getId().equals(dogId)) {
				return d;
			}
		}

		return null;
	}

	public void reserveTraining(TrainingRequest request, Integer userId)
			throws Exception {
		Pass pass = null;
		Worker trainer;
		if (request.getPassId() != null) {
			pass = passRepository.findById(request.getPassId()).orElse(null);
			trainer = pass.getTrainer().getWorkerDetails();
		} else {
			trainer = getWorker(request.getTrainerId(), "trainer");
		}

		if (userId != null) {
			reserveTrainingRegistered(request, pass, trainer, userId);
		} else {
			reserveTrainingUnregistered(request, trainer);
		}
	}

	private Worker getWorker(Integer workerId, String type) {
		Worker worker = workerRepository.findById(workerId).orElse(null);
		if (worker == null || !worker.getUser().getType().equals(type)) {
			throw new RuntimeException("There is no such " + type + ": id = " + workerId);
		}
		return worker;
	}

	private void reserveTrainingRegistered(TrainingRequest request, Pass pass, Worker trainer, Integer userId)
			throws Exception {
		Dog dog = getDog(request, userId);

		if (pass == null) {
			if (request.getNeedPass()) {
				pass = passRepository.save(new Pass(null, dog, trainer.getUser(), null, false));
			}
		}

		createTrainingReserve(dog, trainer, pass, request.getTimes());
	}

	private void reserveTrainingUnregistered(TrainingRequest request, Worker trainer) {
		Dog dog = createDog(request);
		createTrainingReserve(dog, trainer, null, request.getTimes());
	}

	private void createTrainingReserve(Dog dog, Worker trainer, Pass pass, List<LocalDateTime> times) {
		for (LocalDateTime time : times) {
			Reserve reserve = new Reserve(null, dog,
					serviceRepository.findByName("Training"), time);
			reserve = reserveRepository.save(reserve);

			TrainingDetails trainingDetails = new TrainingDetails(reserve.getId(), null, pass, trainer.getUser());
			trainingRepository.save(trainingDetails);
		}
	}

	private Dog createDog(ReserveRequest request) {
		User user = User.builder()
				.id(null)
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.dob(LocalDate.of(2000, 1, 1))
				.phoneNum(request.getPhone())
				.email(request.getEmail())
				.type(accountTypeRepository.findByName("temp"))
				.modifyDate(LocalDate.now())
				.password("")
				.build();
		user = userRepository.save(user);

		Breed breed = breedRepository.findByName(request.getBreed());
		Dog dog = Dog.builder()
				.id(0)
				.name(request.getDogName())
				.dob(LocalDate.now())
				.breed(breed)
				.user(user)
				.build();
		dog = dogRepository.save(dog);

		if (breed == null) {
			OtherBreed otherBreed = OtherBreed.builder()
					.id(dog.getId())
					.name(request.getBreed())
					.size(request.getDogSize())
					.build();
			otherBreedRepository.save(otherBreed);
		}

		return dog;
	}

	public void patchPass(PassPatchRequest request) {
		Pass pass = passRepository.findById(request.getPassId())
				.orElseThrow(() -> new RuntimeException("There is id with id: " + request.getPassId()));
		Dog dog = pass.getDog();
		Worker trainer = pass.getTrainer().getWorkerDetails();

		List<LocalDateTime> newTrainings = new ArrayList<>();
		for (TrainingWrapper wrapper : request.getTimes()) {
			if (wrapper.getId() != null) {
				TrainingDetails training = trainingRepository.findById(wrapper.getId()).orElse(null);
				if (!pass.getTrainings().contains(training)) {
					throw new RuntimeException("Pass don't contains reserve with id: " + wrapper.getId());
				}

				Reserve reserve = training.getReserve();
				reserve.setReserveTime(wrapper.getTime());
				reserveRepository.save(reserve);
			} else {
				newTrainings.add(wrapper.getTime());
			}
		}

		createTrainingReserve(dog, trainer, pass, newTrainings);
	}
}

package org.silly.rats.reserve;

import lombok.RequiredArgsConstructor;
import org.silly.rats.reserve.grooming.GroomingDetails;
import org.silly.rats.reserve.grooming.GroomingService;
import org.silly.rats.reserve.hotel.HotelDetails;
import org.silly.rats.reserve.hotel.HotelService;
import org.silly.rats.reserve.request.PassPatchRequest;
import org.silly.rats.reserve.request.ReserveRequest;
import org.silly.rats.reserve.request.TrainingRequest;
import org.silly.rats.reserve.service.ServiceRepository;
import org.silly.rats.reserve.service.ServiceType;
import org.silly.rats.reserve.training.Pass;
import org.silly.rats.reserve.training.TrainingDetails;
import org.silly.rats.reserve.training.TrainingService;
import org.silly.rats.reserve.training.TrainingWrapper;
import org.silly.rats.user.User;
import org.silly.rats.user.UserRepository;
import org.silly.rats.user.dog.Dog;
import org.silly.rats.user.dog.DogRepository;
import org.silly.rats.user.dog.breed.Breed;
import org.silly.rats.user.dog.breed.BreedRepository;
import org.silly.rats.user.dog.breed.OtherBreed;
import org.silly.rats.user.dog.breed.OtherBreedRepository;
import org.silly.rats.user.type.AccountTypeRepository;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReserveService {
	private final TrainingService trainingService;
	private final GroomingService groomingService;
	private final HotelService hotelService;

	private final ServiceRepository serviceRepository;
	private final ReserveRepository reserveRepository;

	private final DogRepository dogRepository;
	private final BreedRepository breedRepository;
	private final OtherBreedRepository otherBreedRepository;

	private final UserRepository userRepository;
	private final AccountTypeRepository accountTypeRepository;

	public List<Reserve> getAllUserReserves(Integer id, String sortBy, Boolean asc,
											String type, Boolean completed) {
		ServiceType serviceType = serviceRepository.findByName(type);
		Comparator<Reserve> comparator = Comparator.comparing(Reserve::getReserveTime);
		if (sortBy.equals("price")) {
			comparator = Comparator.comparing(Reserve::getPrice);
		} else if (sortBy.equals("dog")) {
			comparator = Comparator.comparing(a -> a.getDog().getName());
		}

		if (!asc) {
			comparator = comparator.reversed();
		}

		return reserveRepository.findByUserId(id)
				.stream()
				.filter(r -> {
					if (completed != null && r.isCompleted() != completed) {
						return false;
					}
					if (serviceType != null && !r.getService().equals(serviceType)) {
						return false;
					}

					return true;
				})
				.sorted(comparator)
				.toList();
	}

	public TrainingDetails getTrainingDetails(Integer userId, Long id) {
		if (!containsReserve(userId, id)) {
			throw new IllegalArgumentException("User " + userId + " don't have reserve with id : " + id);
		}
		return trainingService.getTrainingDetails(id);
	}

	public GroomingDetails getGroomingDetails(Integer userId, Long id) {
		if (!containsReserve(userId, id)) {
			throw new IllegalArgumentException("User " + userId + " don't have reserve with id : " + id);
		}
		return groomingService.getGroomingDetails(id);
	}

	public HotelDetails getHotelDetails(Integer userId, Long id) {
		if (!containsReserve(userId, id)) {
			throw new IllegalArgumentException("User " + userId + " don't have reserve with id : " + id);
		}
		return hotelService.getHotelDetails(id);
	}

	private boolean containsReserve(Integer userId, Long reserveId) {
		User user = userRepository.findById(userId).orElseThrow(() ->
				new IllegalArgumentException("There is no user with id: " + userId));
		for (Dog dog : user.getDogs()) {
			for (Reserve reserve : dog.getReserves()) {
				if (reserve.getId().equals(reserveId)) {
					return true;
				}
			}
		}
		return false;
	}

	public Map<LocalDate, List<String>> getFreeTrainerHours(Integer worker, LocalDateTime start, LocalDateTime end) {
		return trainingService.getFreeHours(worker, start, end);
	}

	public List<Pass> getDogPasses(Integer dogId, Integer userId)
			throws AuthenticationException {
		Dog dog = getUserDog(dogId, userId);
		return trainingService.getDogPasses(dog.getId());
	}

	public void reserveTraining(TrainingRequest request, Integer userId)
			throws AuthenticationException {
		Dog dog;
		if (request.getDogId() == null) {
			dog = createDog(request);
			request.setNeedPass(false);
		} else {
			dog = getDog(request, userId);
		}

		List<Long> reserves = new ArrayList<>(request.getTimes().size());
		for (LocalDateTime time : request.getTimes()) {
			Reserve reserve = createReserves(dog,
					serviceRepository.findByName("training"), time, request.getPrice());
			reserves.add(reserve.getId());
			trainingService.createReserves(reserves, dog, request);
		}
	}

	public void patchPass(PassPatchRequest request, Integer userId) {
		Pass pass = trainingService.getPass(request.getPassId());
		Dog dog = pass.getDog();
		if (!dog.getUser().getId().equals(userId)) {
			throw new IllegalArgumentException("User don't have this dog: id =" + dog.getId());
		}

		List<Long> reserves = new ArrayList<>(request.getTimes().size());
		for (TrainingWrapper wrapper : request.getTimes()) {
			if (wrapper.getId() == null) {
				Reserve reserve = createReserves(dog, serviceRepository.findByName("training"),
						wrapper.getTime(), request.getPrice());
				reserves.add(reserve.getId());
			} else {
				Reserve reserve = trainingService.getReserve(wrapper.getId(), pass);
				reserve.setReserveTime(wrapper.getTime());
				reserveRepository.save(reserve);
			}
		}

		trainingService.createReserves(reserves, dog, pass.getTrainer(), pass);
	}

	private Reserve createReserves(Dog dog, ServiceType service, LocalDateTime time, Double price) {
		return reserveRepository.save(new Reserve(null, dog, service, time, price));
	}

	private Dog getDog(ReserveRequest request, Integer userId)
			throws AuthenticationException {
		if (request.getDogId() == null) {
			throw new IllegalArgumentException("Dog Id must be specified");
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
}

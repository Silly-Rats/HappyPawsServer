package org.silly.rats.user;

import lombok.RequiredArgsConstructor;
import org.silly.rats.user.dog.Dog;
import org.silly.rats.user.dog.DogService;
import org.silly.rats.user.worker.WorkerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {
	private final UserService userService;
	private final DogService dogService;

	@GetMapping(path = "/{type}")
	public List<WorkerInfo> getWorkersByType(@PathVariable String type) {
		return userService.getAllWorkersByType(type);
	}

	@GetMapping(path = "/{id}/dogs")
	public List<Dog> getUserDogs(@PathVariable int id) {
		return dogService.getUserDogs(id);
	}
}

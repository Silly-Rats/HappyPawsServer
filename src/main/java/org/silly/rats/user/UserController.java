package org.silly.rats.user;

import org.silly.rats.user.worker.Worker;
import org.silly.rats.user.worker.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {
	private final UserService userService;
	private final WorkerService workerService;

	@Autowired
	public UserController(UserService userService, WorkerService workerService) {
		this.userService = userService;
		this.workerService = workerService;
	}

	@GetMapping
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@GetMapping(path = "/{type}")
	public List<Worker> getWorkersByType(@PathVariable String type) {
		return workerService.getWorkersByType(type);
	}
}

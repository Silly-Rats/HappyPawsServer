package org.silly.rats.user;

import lombok.RequiredArgsConstructor;
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

	@GetMapping(path = "/{type}")
	public List<User> getUsersByType(@PathVariable String type) {
		return userService.getAllUsersByType(type);
	}
}

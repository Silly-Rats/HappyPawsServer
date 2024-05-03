package org.silly.rats.user;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.silly.rats.user.worker.WorkerInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final JwtService jwtService;

	@GetMapping(path = "/worker/{type}")
	public List<WorkerInfo> getWorkersByType(@PathVariable String type) {
		return userService.getAllWorkersByType(type);
	}

	@GetMapping(path = "/info")
	public User getInfo(@RequestHeader(name = "Authorization") String token) {
		return userService.getUserByToken(token);
	}

	@GetMapping(path = "/type")
	public String getType(@RequestHeader(name = "Authorization") String token) {
		token = token.substring(7);
		return (String) jwtService.extractClaim(token, (c) -> c.get("type"));
	}
}

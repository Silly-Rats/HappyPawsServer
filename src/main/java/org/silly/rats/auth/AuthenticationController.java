package org.silly.rats.auth;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService service;
	private final JwtService jwtService;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(
			@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(service.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticateRequest(
			@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(service.authenticate(request));
	}

	@GetMapping(path = "/type")
	public String getType(@RequestHeader(name = "Authorization") String token) {
		token = token.substring(7);
		return (String) jwtService.extractClaim(token, (c) -> c.get("type"));
	}
}

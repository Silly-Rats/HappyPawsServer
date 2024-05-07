package org.silly.rats.reserve;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.silly.rats.reserve.request.PassPatchRequest;
import org.silly.rats.reserve.request.TrainingRequest;
import org.silly.rats.reserve.training.Pass;
import org.silly.rats.user.UserService;
import org.silly.rats.user.dog.Dog;
import org.silly.rats.user.dog.DogService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/reserve")
@RequiredArgsConstructor
public class ReserveController {
	private final ReserveService reserveService;
	private final JwtService jwtService;

	@GetMapping(path = "/training/free/{worker}")
	public Map<LocalDate, List<String>> getFreeTrainerHours(
			@PathVariable Integer worker,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
		return reserveService.getFreeTrainerHours(worker, start, end);
	}

	@GetMapping(path = "/training/pass/{dog}")
	public List<Pass> getPass(@RequestHeader(name = "Authorization") String token,
							  @PathVariable Integer dog)
			throws AuthenticationException {
		token = token.substring(7);
		Integer userId = (Integer) jwtService.extractClaim(token, (c) -> c.get("id"));

		return reserveService.getDogPasses(dog, userId);
	}

	@PostMapping(path = "/training")
	public void reserveTraining(@RequestHeader(name = "Authorization", required = false) String token,
								@RequestBody TrainingRequest request)
			throws Exception {
		Integer userId = null;
		if (token != null) {
			token = token.substring(7);
			userId = (Integer) jwtService.extractClaim(token, (c) -> c.get("id"));
		}

		reserveService.reserveTraining(request, userId);
	}

	@PatchMapping(path = "/training")
	public void patchPass(@RequestHeader(name = "Authorization", required = false) String token,
								@RequestBody PassPatchRequest request)
			throws Exception {
		reserveService.patchPass(request);
	}
}

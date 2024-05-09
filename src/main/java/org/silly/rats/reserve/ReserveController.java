package org.silly.rats.reserve;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.silly.rats.reserve.grooming.GroomingDetails;
import org.silly.rats.reserve.hotel.HotelDetails;
import org.silly.rats.reserve.request.PassPatchRequest;
import org.silly.rats.reserve.request.TrainingRequest;
import org.silly.rats.reserve.training.Pass;
import org.silly.rats.reserve.training.TrainingDetails;
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

	@GetMapping(path = "/all")
	public List<Reserve> getUserReserves(@RequestHeader(name = "Authorization", required = false) String token,
										 @RequestParam String sortBy,
										 @RequestParam Boolean asc,
										 @RequestParam String type,
										 @RequestParam(required = false) Boolean completed) {
		Integer userId = extractId(token);
		return reserveService.getAllUserReserves(userId, sortBy, asc, type, completed);
	}

	@GetMapping(path = "/training/{id}")
	public TrainingDetails getTrainingDetails(@RequestHeader(name = "Authorization", required = false) String token,
											  @PathVariable Long id) {
		Integer userId = extractId(token);
		return reserveService.getTrainingDetails(userId, id);
	}

	@GetMapping(path = "/grooming/{id}")
	public GroomingDetails getGroomingDetails(@RequestHeader(name = "Authorization", required = false) String token,
											  @PathVariable Long id) {
		Integer userId = extractId(token);
		return reserveService.getGroomingDetails(userId, id);
	}

	@GetMapping(path = "/hotel/{id}")
	public HotelDetails getHotelDetails(@RequestHeader(name = "Authorization", required = false) String token,
										@PathVariable Long id) {
		Integer userId = extractId(token);
		return reserveService.getHotelDetails(userId, id);
	}

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
		Integer userId = extractId(token);
		return reserveService.getDogPasses(dog, userId);
	}

	@PostMapping(path = "/training")
	public void reserveTraining(@RequestHeader(name = "Authorization", required = false) String token,
								@RequestBody TrainingRequest request)
			throws AuthenticationException {
		Integer userId = null;
		if (token != null) {
			userId = extractId(token);
		}

		reserveService.reserveTraining(request, userId);
	}

	@PatchMapping(path = "/training")
	public void patchPass(@RequestHeader(name = "Authorization", required = false) String token,
						  @RequestBody PassPatchRequest request) {
		Integer userId = extractId(token);
		reserveService.patchPass(request, userId);
	}

	private Integer extractId(String token) {
		token = token.substring(7);
		return (Integer) jwtService.extractClaim(token, (c) -> c.get("id"));
	}
}

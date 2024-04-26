package org.silly.rats.reserve;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/reserve")
@RequiredArgsConstructor
public class ReserveController {
	private final ReserveService reserveService;

	@GetMapping(path = "/trainer/free/{worker}")
	public Map<LocalDate, List<String>> getFreeTrainerHours(
			@PathVariable Integer worker,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
		return reserveService.getFreeTrainerHours(worker, start, end);
	}
}

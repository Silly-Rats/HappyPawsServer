package org.silly.rats.reserve.grooming;

import lombok.RequiredArgsConstructor;
import org.silly.rats.user.worker.WorkerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroomingService {
	private final GroomingRepository groomingRepository;
	private final WorkerRepository workerRepository;

	public GroomingDetails getGroomingDetails(Long id) {
		return groomingRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("There is no grooming reserve with id: " + id));
	}
}

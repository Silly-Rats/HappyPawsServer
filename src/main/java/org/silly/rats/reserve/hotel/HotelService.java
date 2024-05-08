package org.silly.rats.reserve.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {
	private final HotelRepository hotelRepository;

	public HotelDetails getHotelDetails(Long id) {
		return hotelRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("There is no hotel reserve with id: " + id));
	}
}

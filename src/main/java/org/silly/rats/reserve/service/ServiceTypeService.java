package org.silly.rats.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceTypeService {
	private final ServiceRepository serviceRepository;

	public ServiceType getServiceTypeById(Byte id) {
		return serviceRepository.findById(id).orElse(null);
	}

	public ServiceType getServiceTypeByName(String name) {
		return serviceRepository.findByName(name);
	}
}

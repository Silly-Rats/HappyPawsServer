package org.silly.rats.reserve.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository
	extends JpaRepository<ServiceType, Byte> {
	ServiceType findByName(String name);
}

package org.silly.rats.user.worker;

import org.silly.rats.user.type.AccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {
	private final WorkerRepository workerRepository;
	private final AccountTypeService accountTypeService;

	@Autowired
	public WorkerService(WorkerRepository workerRepository, AccountTypeService accountTypeService) {
		this.workerRepository = workerRepository;
		this.accountTypeService = accountTypeService;
	}

	public List<Worker> getWorkersByType(String type) {
		Byte type_id = accountTypeService.getAccountTypeByName(type);
		return workerRepository.findByType(type_id);
	}
}

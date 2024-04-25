package org.silly.rats.user.worker;

import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Data
public class WorkerInfo {
	private Integer id;
	private String firstName;
	private String lastName;
	private Double rating;
	private String descriptions;
	private Integer experience;

	public WorkerInfo(Worker worker) {
		id = worker.getWorkerId();
		firstName = worker.getUser().getFirstName();
		lastName = worker.getUser().getLastName();
		rating = worker.getRating();
		descriptions = worker.getDescription();
		experience = Period.between(
				worker.getStartWorking().toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate(), LocalDate.now())
				.getYears();
	}
}

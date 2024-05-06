package org.silly.rats.reserve.training;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.user.User;
import org.silly.rats.user.dog.Dog;
import org.silly.rats.user.worker.WorkerInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pass")
public class Pass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pass_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "dog_id")
	@JsonIgnore
	private Dog dog;

	@ManyToOne
	@JoinColumn(name = "trainer_id")
	@JsonIgnore
	private User trainer;

	@OneToMany(mappedBy = "pass")
	@JsonIgnore
	private List<TrainingDetails> trainings;
	
	private Boolean payed;

	public WorkerInfo getTrainerInfo() {
		return new WorkerInfo(trainer.getWorkerDetails());
	}

	public List<LocalDateTime> getTrainingTimes() {
		List<LocalDateTime> times = new ArrayList<>(trainings.size());

		for (TrainingDetails training : trainings) {
			times.add(training.getReserveTime());
		}

		return times;
	}
}

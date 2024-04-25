package org.silly.rats.reserve;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.user.User;
import org.silly.rats.user.dog.Dog;
import org.silly.rats.user.worker.WorkerInfo;

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
	private Dog dog;

	@ManyToOne
	@JoinColumn(name = "trainer_id")
	private User trainer;

	private Byte used;
	private Byte payed;

	public WorkerInfo getTrainer() {
		return new WorkerInfo(trainer.getWorkerDetails());
	}
}

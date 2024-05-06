package org.silly.rats.reserve.training;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.reserve.Reserve;
import org.silly.rats.reserve.ReserveDetails;
import org.silly.rats.user.User;
import org.silly.rats.user.worker.WorkerInfo;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "training")
public class TrainingDetails implements ReserveDetails {
	@Id
	private Long id;

	@OneToOne
	@JoinColumn(name = "id")
	private Reserve reserve;

	@ManyToOne
	@JoinColumn(name = "pass")
	@JsonBackReference
	private Pass pass;

	@ManyToOne
	@JoinColumn(name = "worker_id")
	private User worker;

	public WorkerInfo getWorker() {
		return new WorkerInfo(worker.getWorkerDetails());
	}
}

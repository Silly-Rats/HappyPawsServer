package org.silly.rats.reserve.grooming;

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
@Table(name = "grooming")
public class GroomingDetails implements ReserveDetails {
	@Id
	private Long id;

	@OneToOne
	@JoinColumn(name = "id")
	private Reserve reserve;

	@ManyToOne
	@JoinColumn(name = "worker_id")
	private User worker;

	@ManyToOne
	@JoinColumn(name = "procedure_id")
	private GroomingProcedure procedure;

	@Column(name = "reserve_time")
	private LocalDateTime reserveTime;

	public WorkerInfo getWorker() {
		return new WorkerInfo(worker.getWorkerDetails());
	}
}

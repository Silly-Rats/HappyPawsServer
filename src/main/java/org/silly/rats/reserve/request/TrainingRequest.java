package org.silly.rats.reserve.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequest extends ReserveRequest {
	private Integer passId;
	private Integer trainerId;
	private Boolean needPass;
	private List<LocalDateTime> times;
}

package org.silly.rats.reserve.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.reserve.training.TrainingWrapper;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassPatchRequest {
	private Integer passId;
	private List<TrainingWrapper> times;
}

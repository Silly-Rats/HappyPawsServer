package org.silly.rats.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionMessage {
	private String message;
	private String uri;
	private String method;
	private int status;
	private long timestamp;
}

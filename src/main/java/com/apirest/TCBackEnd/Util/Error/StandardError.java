package com.apirest.TCBackEnd.Util.Error;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class StandardError implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Instant timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;

}

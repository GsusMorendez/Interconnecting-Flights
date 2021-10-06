package ryanair.interconnecting.flights.errors;

import lombok.Getter;

@Getter
public enum CodeExceptionStore {
	
	DATA_TIME_FORMAT_ERROR("THE DATE FORMAT IS INVALID");
	
	private String code;

	private CodeExceptionStore(String code) {
		this.code = code;
	}
	
	
	
	

}

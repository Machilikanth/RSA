package com.gateway.commonRR;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CommonResponseObject {
	
	private boolean success;
	private String message;
	private Object responseObject;
	private int statusCode;
	
	public static CommonResponseObject buildResponse(boolean success, String message, Object responseObject, int statusCode) {
		CommonResponseObject commonResponseObject = new CommonResponseObject();
		commonResponseObject.setSuccess(success);
		commonResponseObject.setMessage(message);
		commonResponseObject.setResponseObject(responseObject);
		commonResponseObject.setStatusCode(statusCode);
		return commonResponseObject;
	}
}

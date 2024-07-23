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
public class CommonRequestObject {
	private boolean success;
	private String message;
	private Object requestObject;

	public static CommonRequestObject buildRequest(boolean success, String message, Object requestObject) {
		CommonRequestObject commonRequestObject = new CommonRequestObject();
		commonRequestObject.setSuccess(success);
		commonRequestObject.setMessage(message);
		commonRequestObject.setRequestObject(requestObject);
		return commonRequestObject;
	}

}

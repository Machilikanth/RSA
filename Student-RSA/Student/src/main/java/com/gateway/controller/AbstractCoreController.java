package com.gateway.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gateway.RSA.KeyService;
import com.gateway.commonRR.CommonResponseObject;
import com.gateway.commonRR.KeyPairResponse;
import com.gateway.model.DecryptRequest;
import com.gateway.model.Student;

@RestController
@RequestMapping("/Student")
public class AbstractCoreController<T> {

	

	@Autowired
	private KeyService keyService;

	@PostMapping("/generate-keys")
	public CommonResponseObject generateKeys() {
		try {
			KeyPairResponse keyPairResponse = keyService.generateAndStoreKeyPair();

			Map<String, Object> responseData = new HashMap<>();
			responseData.put("publicKey", keyPairResponse.getPublicKey());
			responseData.put("id", keyPairResponse.getId());

			return CommonResponseObject.buildResponse(true, "Keys generated successfully", responseData, 200);
		} catch (NoSuchAlgorithmException e) {
			return CommonResponseObject.buildResponse(false, "Error generating keys", e.getMessage(), 500);
		}
	}
	
	@PostMapping("/get-by-id")
	public CommonResponseObject getById(@RequestParam("id") String id) {
		try {
			Student keyPair = keyService.getKeyPairById(id);
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("id", id);

			responseData.put("privateKey", keyPair.getPrivateKey());

			return CommonResponseObject.buildResponse(true, "Key pair retrieved successfully", responseData, 200);
		} catch (NoSuchElementException e) {
			return CommonResponseObject.buildResponse(false, "Key pair not found", e.getMessage(), 404);
		}
	}

	@PostMapping("/decrypt")
	public CommonResponseObject decryptData(@RequestBody DecryptRequest decryptRequest) {
	    try {
	        String decryptedData = keyService.decryptData(decryptRequest.getEncryptedData(), decryptRequest.getKeyId());
	        return CommonResponseObject.buildResponse(true, "Data decrypted successfully", decryptedData, 200);
	    } catch (Exception e) {
	        return CommonResponseObject.buildResponse(false, "Error decrypting data", e.getMessage(), 500);
	    }
	}


	

}

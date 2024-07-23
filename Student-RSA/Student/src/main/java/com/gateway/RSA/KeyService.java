package com.gateway.RSA;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.UUID;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.commonRR.KeyPairResponse;
import com.gateway.model.Student;
import com.gateway.repo.StudentRepository;

@Service
public class KeyService {

    @Autowired
    private StudentRepository keyPairRepository;

    @Autowired
    private RSAKeyGenerator rsaKeyGenerator;

    public KeyPairResponse generateAndStoreKeyPair() throws NoSuchAlgorithmException {
        KeyPair keyPair = rsaKeyGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        
        Student keyPairEntity = new Student();
        String id = UUID.randomUUID().toString();
        keyPairEntity.setId(id);
        keyPairEntity.setPrivateKey(Base64.getEncoder().encodeToString(privateKey.getEncoded()));    
        keyPairRepository.save(keyPairEntity);

        KeyPairResponse response = new KeyPairResponse();
        response.setPublicKey(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        response.setId(id);

        return response;
    }
    
    public Student getKeyPairById(String id) {
        return keyPairRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Key pair not found for id: " + id));
    }


   
    
    
    private PrivateKey getPrivateKeyById(String keyId) throws Exception {
        Student student = keyPairRepository.findById(keyId)
            .orElseThrow(() -> new Exception("Key not found"));
        byte[] keyBytes = Base64.getDecoder().decode(student.getPrivateKey());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        return keyFactory.generatePrivate(keySpec);
    }

    
    
    public String decryptData(String encryptedData, String keyId) throws Exception {
        PrivateKey privateKey = getPrivateKeyById(keyId);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    
    
    
}

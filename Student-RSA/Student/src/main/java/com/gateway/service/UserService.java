package com.gateway.service;

import java.security.KeyPair;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.dto.StudentDto;
import com.gateway.model.Student;
import com.gateway.repo.StudentRepository;

@Service
public class UserService extends AbstractService<Student> {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private StudentRepository userRepository;

	@Override
	public Class<Student> getEntityClass() {
		return Student.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityDTO() {
		return (T) new StudentDto();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityObject() {
		return (T) new Student();
	}
	
}

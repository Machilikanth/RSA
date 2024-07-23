package com.gateway.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public abstract class AbstractService<T> {

	@Autowired
	protected MongoTemplate mongoTemplate;

	@SuppressWarnings("unchecked")
	public <T> T convertObjectPerEnum(Object obj, Class clazz) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		return (T) objectMapper.convertValue(obj, clazz == null ? this.getEntityClass() : clazz);

	}

	@SuppressWarnings("unchecked")
	public <T> T entityToDTO(Object obj) {
		Object dto = this.getEntityDTO();
		BeanUtils.copyProperties(obj, dto);
		return (T) dto;
	}

	@SuppressWarnings("unchecked")
	public <T> T dtoToEntity(Object dto) {
		Object obj = this.getEntityObject();
		BeanUtils.copyProperties(dto, obj);
		return (T) obj;
	}

	public Class<?> getEntityClass() {
		return null;
	}

	public <T> T getEntityObject() {
		return null;
	}

	public <T> T getEntityDTO() {
		return null;
	}

	public T create(T entity) {
		return (T) mongoTemplate.insert(entity);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll(T entity) {
		return (List<T>) mongoTemplate.findAll(entity.getClass());
	}

	@SuppressWarnings("unchecked")
	public T getById(String id) {
		return (T) mongoTemplate.findById(id, this.getEntityClass());
	}

	public T update(T updatedEntity) {
		return (T) mongoTemplate.save(updatedEntity);
	}

}

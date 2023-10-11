package com.tcs.training.student.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.training.shared.event.entity.Event;
import com.tcs.training.shared.event.impl.DatabaseEventQueue;
import com.tcs.training.shared.event.model.Status;
import com.tcs.training.student.config.datasource.ClientDatabase;
import com.tcs.training.student.config.datasource.ClientDatabaseContextHolder;
import com.tcs.training.student.entity.Student;
import com.tcs.training.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

	private final StudentService studentService;
	private final DatabaseEventQueue databaseEventQueue;

	@PostMapping("/register")
	public ResponseEntity<Student> add(@RequestBody Student student) throws JsonProcessingException {

		student.setStudentId(null);
		student = studentService.add(student);

		ClientDatabaseContextHolder.set(ClientDatabase.SHARED);
		log.info("Switching 3 : {}", ClientDatabaseContextHolder.getClientDatabase());
		ObjectMapper objectMapper = new ObjectMapper();
		Event event = Event.builder().eventData(objectMapper.writeValueAsString(student)).eventType("student-registration").status(Status.NEW).build();
		event.setServiceName("library-service");
		databaseEventQueue.publish(event);
		event.setServiceName("finance-service");
		databaseEventQueue.publish(event);
		ClientDatabaseContextHolder.clear();
		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}

}

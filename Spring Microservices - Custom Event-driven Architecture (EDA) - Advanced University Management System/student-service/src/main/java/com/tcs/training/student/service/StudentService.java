package com.tcs.training.student.service;

import com.tcs.training.student.entity.Student;
import com.tcs.training.student.exception.NoDataFoundException;
import com.tcs.training.student.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

	private final StudentRepository studentRepository;

	public List<Student> getAll() {
		return studentRepository.findAll();
	}

	public Student getById(Long id) {
		return studentRepository.findById(id).orElseThrow();
	}

	public List<Student> getByIds(Set<Long> ids) {
		List<Student> students = studentRepository.findAllById(ids);
		log.info("products :: {}", students);
		if (ids.size() != students.size()) {
			throw new NoDataFoundException("Requested students not available.");
		}
		return students;
	}

	@Transactional
	public Student add(@RequestBody Student student) {
		return studentRepository.save(student);
	}

	@Transactional
	public Student put(@RequestBody Student student) {
		return studentRepository.save(student);
	}

	@Transactional
	public void delete(@PathVariable("id") Long id) {
		studentRepository.deleteById(id);
	}

}

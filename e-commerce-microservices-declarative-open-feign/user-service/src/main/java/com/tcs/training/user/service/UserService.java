package com.tcs.training.user.service;

import com.tcs.training.user.entity.User;
import com.tcs.training.user.exception.NoDataFoundException;
import com.tcs.training.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User getById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new NoDataFoundException("No User found with id : "+id));
	}

	public List<User> getByIds(Set<Long> ids) {
		return userRepository.findAllById(ids);
	}

	@Transactional
	public User add(@RequestBody User user) {
		return userRepository.save(user);
	}

	@Transactional
	public User put(@RequestBody User user) {
		return userRepository.save(user);
	}

	@Transactional
	public void delete(@PathVariable("id") Long id) {
		userRepository.deleteById(id);
	}

}

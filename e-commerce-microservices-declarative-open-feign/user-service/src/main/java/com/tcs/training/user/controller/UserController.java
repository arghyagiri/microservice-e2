package com.tcs.training.user.controller;

import com.tcs.training.user.entity.User;
import com.tcs.training.user.model.exception.UserDTO;
import com.tcs.training.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping
	public List<User> getAll() {
		return userService.getAll();
	}

	@GetMapping(value = "/{id}")
	public User getById(@PathVariable("id") Long id) {
		return userService.getById(id);
	}

	@GetMapping(value = "/get-by-ids")
	public List<User> getByIds(@RequestParam("id") Set<Long> ids) {
		return userService.getByIds(ids);
	}

	@PostMapping()
	public User add(@RequestBody @Valid UserDTO author) {
		User userEntity = new User();
		BeanUtils.copyProperties(author, userEntity);
		return userService.add(userEntity);
	}

	@PutMapping()
	public User put(@RequestBody User user) {
		return userService.put(user);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		userService.delete(id);
	}


}

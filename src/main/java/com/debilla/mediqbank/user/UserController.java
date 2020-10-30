package com.debilla.mediqbank.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping(path="/users") // Map ONLY POST Requests
	public @ResponseBody String addNewUser (@RequestParam String name
		, @RequestParam String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		return "Saved";
	}

	@GetMapping(path="/users")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}

	@GetMapping(path="/users/{id}")
	public @ResponseBody User getUser(@PathVariable int id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent())
			throw new UserNotFoundException("id-" + id);

		return user.get();
	}

	@PatchMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable int id) throws UserNotFoundException {
		Optional<User> userOptional = userRepository.findById(id);

		if (!userOptional.isPresent())
			return ResponseEntity.notFound().build();

		user.setId(id);

		userRepository.save(user);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateWholeUser(@RequestBody User user, @PathVariable int id) throws UserNotFoundException {
		Optional<User> userOptional = userRepository.findById(id);

		if (!userOptional.isPresent())
			return ResponseEntity.notFound().build();

		user.setId(id);

		userRepository.save(user);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/users/{id}")
	public void deleteUSer(@PathVariable int id) {
		userRepository.deleteById(id);
	}
}
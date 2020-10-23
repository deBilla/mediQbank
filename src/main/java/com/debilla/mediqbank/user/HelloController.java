package com.debilla.mediqbank;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.debilla.mediqbank.user.User;
import com.debilla.mediqbank.user.UserRepository;

@RestController
public class HelloController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Debilla";
	}

	@Autowired
	private UserRepository userRepository;

	@PostMapping(path="/add") // Map ONLY POST Requests
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

	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}

	@Autowired
	private CustomerRepository cusRepo;
	@PostMapping(path="/addCustomer") // Map ONLY POST Requests
	public @ResponseBody String addNewCustomer (@RequestParam String name, @RequestParam String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		Customer n = new Customer();
		n.setName(name);
		n.setEmail(email);
		cusRepo.save(n);
		return "Saved";
	}

	@GetMapping(path="/findcus")
	public @ResponseBody Iterable<Customer> getAllCustomers() {
		// This returns a JSON or XML with the users
		return cusRepo.findAll();
	}

}
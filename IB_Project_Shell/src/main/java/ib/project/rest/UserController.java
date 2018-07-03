package ib.project.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ib.project.model.Authority;
import ib.project.model.User;
import ib.project.model.UserDTO;
import ib.project.service.AuthorityService;
import ib.project.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	PasswordEncoder passwordEncoder;

	// Putanja => localhost:8080/api/user/1

	// Za pristup ovoj metodi neophodno je da ulogovani korisnik ima ADMIN ulogu
	// Ukoliko nema, server ce vratiti gresku 403 Forbidden
	// Korisnik jeste autentifikovan, ali nije autorizovan da pristupi resursu
	@RequestMapping(method = GET, value = "/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public User loadById(@PathVariable Integer userId) {
		return this.userService.findById(userId);
	}

	@RequestMapping(method = GET, value = "/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> loadAll() {
		return this.userService.findAll();
	}

	@RequestMapping("/whoami")
	@PreAuthorize("hasRole('USER')")
	public User user(Principal user) {
		return this.userService.findByEmail(user.getName());
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDTO>> getUsers() {
		List<User> users = userService.findAll();
		List<UserDTO> userDTOS = new ArrayList<>();
		for (User u : users) {
			userDTOS.add(new UserDTO(u));
		}
		return new ResponseEntity<List<UserDTO>>(userDTOS, HttpStatus.OK);
	}

	@GetMapping(value = "/all/active")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<UserDTO>> getAllActive() {
		List<UserDTO> active = new ArrayList<>();
		List<User> all = userService.findAll();
		for (User user : all) {
			if (user.isActive())
				active.add(new UserDTO(user));
		}
		return new ResponseEntity<List<UserDTO>>(active, HttpStatus.OK);
	}

	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		System.out.println("Servis aktiviran");
		User u = new User();
		u.setPassword(passwordEncoder.encode(user.getPassword()));
		u.setEmail(user.getEmail());
		u.setActive(false);
		u.setCertificate("");
		u = userService.save(u);
		Authority authority = authorityService.findByName("ROLE_USER");
		u.getUser_authorities().add(authority);
		return new ResponseEntity<User>(u, HttpStatus.CREATED);
	}

	@PutMapping(value = "/edit", consumes = "application/json")
	public ResponseEntity<UserDTO> updateUser(@RequestBody String email) {
		User user = userService.findByEmail(email);
		System.out.println("Pronadjeni user: " + user);
		if (user == null)
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		user.setActive(true);
		user = userService.save(user);
		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
	}
}

package com.example.tutoperso;

import com.example.tutoperso.exception.*;
import com.example.tutoperso.model.User;
import com.example.tutoperso.repositories.UserRepository;
import com.example.tutoperso.service.UserService;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.*;
import java.util.function.Function;


@SpringBootTest
public class Apptest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Test
	public void contextLoads() {

	}

	@Test
	public void createUserTest() {
		userRepository.deleteAll();
		User user = new User().makeUsername("test");
		userService.createUser(user);
		User found = userRepository.findOneByUsername(user.getUsername()).orElse(null);

		assert found != null;
	}

	@Test
	public void createUserAlreadyExistExceptionTest()
	{
		userRepository.deleteAll();
		User user = new User().makeUsername("test");
		userService.createUser(user);
		try {
			userService.createUser(user);
			assert false;
		}
		catch(UserAlreadyExistException e) {
			assert true;
		}
	}

	@Test
	public void createNotValidUsernameExceptionTest()
	{
		userRepository.deleteAll();
		User user = new User().makeUsername("");
		try {
			userService.createUser(user);
			assert false;
		}
		catch(NotValidUsernameException e) {
			assert true;
		}
	}

	@Test
	public void updateUserTest()
	{
		userRepository.deleteAll();
		User user = new User().makeUsername("test").makeNom("Avant");
		userService.createUser(user);
		user.setNom("Après");
		Long id = userRepository.findOneByUsername(user.getUsername()).get().getId();
		userService.updateUser(user);

		User found = userRepository.findOneByUsername(user.getUsername()).get();

		assert found.getUsername().equals(user.getUsername());
	}

	@Test
	public void updateUserExceptionTest()
	{
		userRepository.deleteAll();
		User user = new User().makeUsername("test");
		try {
			userService.updateUser(user);
			assert false;
		}
		catch(UserNotFoundException e) {
			assert true;
		}
	}

	@Test
	public void deleteUserTest()
	{
		userRepository.deleteAll();
		User user = new User().makeUsername("test");
		userService.createUser(user);

		userService.deleteUser(user.getUsername());

		User found = userRepository.findOneByUsername(user.getUsername()).orElse(null);

		assert found == null;
	}

	@Test
	public void deleteUserNotFoundExceptionTest()
	{
		userRepository.deleteAll();
		User user = new User().makeUsername("test");
		try {
			userService.deleteUser(user.getUsername());
			assert false;
		}
		catch(UserNotFoundException e) {
			assert true;
		}
	}

	@Test
	public void getUserTest()
	{
		userRepository.deleteAll();
		User user = new User().makeUsername("test");
		userService.createUser(user);
		User found = userService.getUser(user.getUsername());

		assert user.getUsername().equals(found.getUsername());
	}

	@Test
	public void getUserNotFoundExceptionTest()
	{
		userRepository.deleteAll();
		try {
			userService.getUser("test");
			assert false;
		}
		catch(UserNotFoundException e) {
			assert true;
		}
	}

	@Test
	public void getAllUsersTest()
	{
		userRepository.deleteAll();
		User user1 = new User().makeUsername("test1");
		userService.createUser(user1);
		User user2 = new User().makeUsername("test2");
		userService.createUser(user2);
		User user3 = new User().makeUsername("test3");
		userService.createUser(user3);

		List<String> listeUser = new ArrayList<>();
		listeUser.add(user1.getUsername());
		listeUser.add(user2.getUsername());
		listeUser.add(user3.getUsername());

		Pageable pageable = PageRequest.of(0, 3);

		List<String> listeUserFound = new ArrayList<>();
		listeUserFound.add(userRepository.findAll(pageable).getContent().get(0).getUsername());
		listeUserFound.add(userRepository.findAll(pageable).getContent().get(1).getUsername());
		listeUserFound.add(userRepository.findAll(pageable).getContent().get(2).getUsername());


		assert listeUserFound.containsAll(listeUser);
	}

	@Test
	public void getAllIncorrectPageParamExceptionTest()
	{
		userRepository.deleteAll();
		try {
			userService.getAllUsers(-1,-1);
			assert false;
		}
		catch(IncorrectPageParamException e) {
			assert true;
		}
	}

	@Test
	public void getAllIncorrectSizePageParamExceptionTest()
	{
		userRepository.deleteAll();
		try {
			userService.getAllUsers(0,0);
			assert false;
		}
		catch(IncorrectSizePageParamException e) {
			assert true;
		}
	}

}

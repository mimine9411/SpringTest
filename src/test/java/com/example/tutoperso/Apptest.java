package com.example.tutoperso;

import com.example.tutoperso.model.User;
import com.example.tutoperso.repositories.UserRepository;
import com.example.tutoperso.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Apptest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Test
	public void createUserTest() {
		userRepository.deleteAll();
		User user = new User();
		user.setUsername("test");
		userService.createUser(user);

		User found = userRepository.findOneByUsername(user.getUsername()).get();

		assert found.getUsername().equals(user.getUsername());
	}

	@Test
	public void updateUserTest()
	{
		userRepository.deleteAll();
		User user = new User();
		user.setUsername("test");
		user.setNom("Avant");
		userService.createUser(user);
		user.setNom("Après");
		userService.updateUser(user);

		User found = userRepository.findOneByUsername(user.getUsername()).get();

		assert found.getUsername().equals(user.getUsername());
	}

	@Test
	public void deleteUserTest()
	{
		userRepository.deleteAll();
		User user = new User();
		user.setUsername("test");
		userService.createUser(user);
		userService.deleteUser(userRepository.findOneByUsername(user.getUsername()).get().getId());

		User found = userRepository.findOneByUsername(user.getUsername()).orElse(null);

		assert found == null;
	}

	@Test
	public void getUserTest()
	{
		userRepository.deleteAll();
		User user = new User();
		user.setUsername("test");
		userService.createUser(user);
		User get = userService.getUser(userRepository.findOneByUsername(user.getUsername()).get().getId());

		assert user.getUsername().equals(get.getUsername());
	}

	@Test
	public void getAllUsersTest()
	{
		userRepository.deleteAll();
		User user1 = new User();
		user1.setUsername("test1");
		userService.createUser(user1);
		User user2 = new User();
		user2.setUsername("test2");
		userService.createUser(user2);
		User user3 = new User();
		user3.setUsername("test3");
		userService.createUser(user3);

		List<String> listeUsername = new ArrayList<>();
		listeUsername.add(user1.getUsername());
		listeUsername.add(user2.getUsername());
		listeUsername.add(user3.getUsername());

		List<String> getAllUsername = new ArrayList<>();

		getAllUsername.add(userService.getAllUsers(0,3).getContent().get(0).getUsername());
		getAllUsername.add(userService.getAllUsers(0,3).getContent().get(1).getUsername());
		getAllUsername.add(userService.getAllUsers(0,3).getContent().get(2).getUsername());

		assert listeUsername.equals(getAllUsername);
	}

}

package com.example.tutoperso;

import com.example.tutoperso.exception.*;
import com.example.tutoperso.model.User;
import com.example.tutoperso.repositories.UserRepository;
import com.example.tutoperso.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void createUserTest() {
        User user = new User().makeUsername("test");
        when(userRepository.save(user)).thenReturn(user);
        assert userService.createUser(user).equals(userRepository.save(user));
    }

    @Test(expected = UserAlreadyExistException.class)
    public void createUserAlreadyExistExceptionTest()
    {
        User user = new User().makeUsername("test");
        when(userRepository.findOneByUsername("test")).thenReturn(Optional.of(user));
        userService.createUser(user);
    }

    @Test(expected = NotValidUsernameException.class)
    public void createNotValidUsernameExceptionTest()
    {
        User user = new User().makeUsername("");
        userService.createUser(user);
    }

    @Test
    public void updateUserTest()
    {
        User user = new User().makeUsername("test").makeNom("Avant");
        when(userRepository.findOneByUsername("test")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user.makeNom("apres"));
        assert userService.updateUser(user).equals(userRepository.findOneByUsername("test").get());
    }

    @Test(expected = UserNotFoundException.class)
    public void updateUserExceptionTest()
    {
        User user = new User().makeUsername("test").makeNom("Avant");
        userService.updateUser(user);
    }

    @Test
    public void deleteUserTest()
    {
        User user = new User().makeUsername("test");
        when(userRepository.findOneByUsername("test")).thenReturn(Optional.of(user));
        userService.deleteUser("test");
        verify(userRepository, times(1)).delete(user);
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteUserNotFoundExceptionTest()
    {
        userService.deleteUser("test");
    }

    @Test
    public void getUserTest()
    {
        when(userRepository.findOneByUsername("test")).thenReturn(Optional.of(new User().makeUsername("test")));
        assert userRepository.findOneByUsername("test").get().equals(userService.getUser("test"));
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserNotFoundExceptionTest()
    {
        userService.getUser("test");
    }

    @Test
    public void getAllUserTest()
    {
        Pageable pageable = PageRequest.of(0, 3);
        Page<User> page = Page.empty(pageable);
        when(userRepository.findAll(pageable)).thenReturn(page);
        assert userRepository.findAll(pageable).equals(userService.getAllUsers(0,3));
    }

    @Test(expected = IncorrectPageParamException.class)
    public void getAllIncorrectPageParamExceptionTest()
    {
        userService.getAllUsers(-1,0);
    }

    @Test(expected = IncorrectSizePageParamException.class)
    public void getAllIncorrectSizePageParamExceptionTest()
    {
        userService.getAllUsers(0,0);
    }
}

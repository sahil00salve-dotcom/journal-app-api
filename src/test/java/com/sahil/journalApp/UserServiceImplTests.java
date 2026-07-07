package com.sahil.journalApp;

import com.sahil.journalApp.Entity.User;
import com.sahil.journalApp.Repository.UserRepository;
import com.sahil.journalApp.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testbyusernamebytest(){
       User user=new User();



        // Verify password is encoded
//        assertNotEquals("123", saved.getPassword());


    }
}

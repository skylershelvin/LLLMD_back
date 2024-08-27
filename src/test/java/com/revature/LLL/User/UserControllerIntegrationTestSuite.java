package com.revature.LLL.User;


import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTestSuite {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPostRequestValidUserThenCorrectResponse() throws Exception {
        String validUser =
                "{\n" +
                        "\"firstName\": \"John\",\n" +
                        "\"lastName\": \"Doe\",\n" +
                        "\"email\": \"test2@email.com\",\n" +
                        "\"password\": \"password\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(validUser))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    public void testPutUpdateUser() throws Exception{
        String validUser =
                "{\n" +
                        "\"firstName\": \"John\",\n" +
                        "\"lastName\": \"Doe\",\n" +
                        "\"email\": \"john.doe@example.com\",\n" +
                        "\"password\": \"password3\"\n" +
                        "}";


        doNothing().when(userService).update((com.revature.LLL.User.User));

        mockMvc.perform(put("updatePassword"));



    }
}

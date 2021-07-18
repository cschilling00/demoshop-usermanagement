package src.test.com.novatec.usermanagement.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import src.main.com.novatec.usermanagement.UserManagementApplication
import src.main.com.novatec.usermanagement.model.User
import src.main.com.novatec.usermanagement.repository.UserRepository


@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [UserManagementApplication::class])
class UserControllerTest(@Autowired val mockMvc: MockMvc,
                         @Autowired val userRepository: UserRepository) {

    val token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoicHJvZHVjdHNlcnZpY2UtYXBpIiwiaWQiOiI2MDJhNzQxNjRmOWZmNjQwOGFhZDVkYTYiLCJpYXQiOjE2MjU2ODAzNjN9.TIm2oPpBxcMqVh8lfV_0aj-bcLgK84jn2HJ0wpchZRzWyu-DkozJr5QkPMwCPPBnnYjUQIM1C5c0WjBKgCEgAQ"


    @BeforeAll
    fun loadRepository(){
        userRepository.deleteAll()
        userRepository.save(User("602a74164f9ff6408aad5da6", "user", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi", "user", "cs00@test.de", listOf("Friedrichstr. 70", "88433 Ingerkingen")))
        userRepository.save(User("602a74164f9ff6408aad5da7", "admin", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi", "user,admin", "cs00@test.de", listOf("Friedrichstr. 70", "88433 Ingerkingen")))
    }


    @Test
    fun `should login with user credentials`(){
        val requestBody = "{\"username\": \"user\",\"password\": \"password\"}"

        val result = mockMvc.perform(post("/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
        )
            .andExpect { ResponseEntity.status(HttpStatus.OK) }
            .andReturn()

        assert(result.response.contentAsString.contains("token"))
        assert(result.response.contentAsString.contains("eyJ0eXAiOiJKV1QiL"))
        assert(result.response.contentAsString.contains("userId"))
        assert(result.response.contentAsString.contains("602a74164f9ff6408aad5da6"))
    }

    @Test
    fun `should get authorities`(){
        val expectedResponse = "user"
        val result = mockMvc.perform(get("/users/authorities").header("Authorization", token))
            .andExpect { ResponseEntity.status(HttpStatus.OK) }
            .andReturn()

        assertEquals(expectedResponse, result.response.contentAsString)
    }
}
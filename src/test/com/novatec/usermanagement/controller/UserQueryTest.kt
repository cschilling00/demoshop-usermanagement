package src.test.com.novatec.usermanagement.controller

import com.auth0.jwt.JWTVerifier
import com.graphql.spring.boot.test.GraphQLTestTemplate
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import src.main.com.novatec.usermanagement.UserManagementApplication
import src.main.com.novatec.usermanagement.model.User
import src.main.com.novatec.usermanagement.repository.UserRepository
import java.io.File
import java.nio.charset.Charset

@ExtendWith(
    SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [UserManagementApplication::class])
class UserQueryTest(@Autowired val graphQLTestTemplate: GraphQLTestTemplate, @Autowired val userRepository: UserRepository
) {

    @BeforeEach
    fun setHeaderForUser() {
        graphQLTestTemplate.addHeader(
            "Authorization",
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoicHJvZHVjdHNlcnZpY2UtYXBpIiwiaWQiOiI2MDJhNzQxNjRmOWZmNjQwOGFhZDVkYTYiLCJpYXQiOjE2MjU2ODAzNjN9.TIm2oPpBxcMqVh8lfV_0aj-bcLgK84jn2HJ0wpchZRzWyu-DkozJr5QkPMwCPPBnnYjUQIM1C5c0WjBKgCEgAQ"
        )
    }

    @BeforeAll
    fun loadRepository(){
        userRepository.deleteAll()
        userRepository.save(User("602a74164f9ff6408aad5da6", "user", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi", "user", "cs00@test.de", listOf("Friedrichstr. 70", "88433 Ingerkingen")))
        userRepository.save(User("602a74164f9ff6408aad5da7", "admin", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi", "user,admin", "cs00@test.de", listOf("Friedrichstr. 70", "88433 Ingerkingen")))
    }

    @Test
    fun `should login with user credentials`(){
        graphQLTestTemplate.clearHeaders()
        var response = graphQLTestTemplate.postForResource("request/login.graphql")
        val expectedResponse = File("src/test/resources/response/loginRes.json").readText(Charset.defaultCharset())
        Assertions.assertNotNull(response)
        Assertions.assertTrue(response.isOk)
        JSONAssert.assertEquals(expectedResponse, response.rawResponse.body, true)
    }

    @Test
    fun `should get authorities`(){
        var response = graphQLTestTemplate.postForResource("request/authorities.graphql")
        val expectedResponse = File("src/test/resources/response/authoritiesRes.json").readText(Charset.defaultCharset())
        Assertions.assertNotNull(response)
        Assertions.assertTrue(response.isOk)
        JSONAssert.assertEquals(expectedResponse, response.rawResponse.body, true)
    }


}
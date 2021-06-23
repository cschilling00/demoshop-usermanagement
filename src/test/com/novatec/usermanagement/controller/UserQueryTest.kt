package src.test.com.novatec.usermanagement.controller

import com.graphql.spring.boot.test.GraphQLTestTemplate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import src.main.com.novatec.usermanagement.UserManagementApplication

@ExtendWith(
    SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [UserManagementApplication::class])
class UserQueryTest(@Autowired val graphQLTestTemplate: GraphQLTestTemplate
) {

    @Test
    fun `should get all users`(){
        var response = graphQLTestTemplate.postForResource("users.graphql")
        println("response: "+response.rawResponse.body.toString())
        Assertions.assertNotNull(response)
        Assertions.assertTrue(response.isOk)
        Assertions.assertEquals("602a74164f9ff6408aad5da6", response.get("$.data.getUsers[0].id"))
    }

    @Test
    fun `should get user with id 602a74164f9ff6408aad5da6`(){
        var response = graphQLTestTemplate.postForResource("users.graphql")
        println("response: "+response)
        Assertions.assertNotNull(response)
        Assertions.assertTrue(response.isOk)
        Assertions.assertEquals("602a74164f9ff6408aad5da6", response.get("$.data.getUserById[0].id"))
    }


}
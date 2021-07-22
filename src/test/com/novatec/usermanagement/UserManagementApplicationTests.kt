package src.test.com.novatec.usermanagement


import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import src.main.com.novatec.usermanagement.UserManagementApplication

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [UserManagementApplication::class])
class UserManagementApplicationTests {

	@Test
	fun contextLoads() {
	}

}

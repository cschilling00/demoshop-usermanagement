package src.main.com.novatec.usermanagement

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import src.main.com.novatec.usermanagement.model.User
import src.main.com.novatec.usermanagement.repository.UserRepository

@SpringBootApplication
class UserManagementApplication(var userRepository: UserRepository) : CommandLineRunner {
	override fun run(vararg args: String?) {
		userRepository.deleteAll()
		userRepository.save(User("602a74164f9ff6408aad5da6", "user", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi", "user", "cs00@test.de", listOf("Friedrichstr. 70", "88433 Ingerkingen")))
		userRepository.save(User("602a74164f9ff6408aad5da7", "admin", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi", "user,admin", "cs00@test.de", listOf("Friedrichstr. 70", "88433 Ingerkingen")))
		println(userRepository.findAll())
	}
}

fun main(args: Array<String>) {

	runApplication<UserManagementApplication>(*args) {}

}

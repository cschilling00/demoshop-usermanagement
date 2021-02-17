package src.main.com.novatec.graphql.usermanagement

import org.bson.types.ObjectId
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import src.main.com.novatec.graphql.usermanagement.model.User
import src.main.com.novatec.graphql.usermanagement.repository.UserRepository

@SpringBootApplication
class UserManagementApplication(var userRepository: UserRepository) : CommandLineRunner {
	override fun run(vararg args: String?) {
		userRepository.deleteAll()
		userRepository.save(User("602a74164f9ff6408aad5da6", "user", "password", "user", "", listOf("Reuteäckerstr. 70", "88433 Ingerkingen"), listOf("")))
		println(userRepository.findAll())
	}
}

fun main(args: Array<String>) {

	runApplication<UserManagementApplication>(*args) {}


}
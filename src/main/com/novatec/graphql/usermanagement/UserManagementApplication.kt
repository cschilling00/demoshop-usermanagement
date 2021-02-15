package src.main.com.novatec.graphql.usermanagement

import com.expediagroup.graphql.SchemaGeneratorConfig
import com.expediagroup.graphql.TopLevelObject
import com.expediagroup.graphql.toSchema
import graphql.Scalars
import graphql.schema.*
import org.bson.types.ObjectId
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import src.main.com.novatec.graphql.usermanagement.controller.UserMutation
import src.main.com.novatec.graphql.usermanagement.controller.UserQuery
import src.main.com.novatec.graphql.usermanagement.model.User
import src.main.com.novatec.graphql.usermanagement.repository.UserRepository


@SpringBootApplication
class UserManagementApplication(var userRepository: UserRepository): CommandLineRunner {
	override fun run(vararg args: String?) {
		userRepository.deleteAll()
		userRepository.save(User(ObjectId.get().toString(), "user", "password", "user", "", listOf("Reuteäckerstr. 70", "88433 Ingerkingen"), listOf("")))
		println(userRepository.findAll())
	}
}

fun main(args: Array<String>) {

	runApplication<UserManagementApplication>(*args) {}

}

val schema = toSchema(
		config = SchemaGeneratorConfig(supportedPackages = listOf("src.main.com.novatec.graphql.usermanagement")),
		queries = listOf(TopLevelObject(UserQuery()), TopLevelObject(UserMutation()))
)


package src.main.com.novatec.graphql.usermanagement.service

import src.main.com.novatec.graphql.usermanagement.model.User
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import src.main.com.novatec.graphql.usermanagement.repository.UserRepository


@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    fun getUserById(id: String): User? {
        return userRepository.findById(id).orElseThrow {
            throw NoSuchElementException("User with id ´$id´ not found")
        }
    }

    fun getUser(): List<User?> {
        return userRepository.findAll()
    }

    fun updateUser(user: User): User? {
        if (user.id == null) {
            throw IllegalArgumentException("No Id given")
        } else {
            userRepository.findById(user.id).orElseThrow {
                throw NoSuchElementException("User with id ´$user.id´ not found")
            }
            return userRepository.save(user)
        }
    }

    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    fun deleteUser(userId: String): String {
        userRepository.findById(userId).orElseThrow {
            throw NoSuchElementException("User with id ´$userId´ not found")
        }
        userRepository.deleteById(userId)
        return "User successfully deleted"
    }

    fun checkCredentials(user: User): Boolean {
        return userRepository.findByUsername(user.username).password == user.password
    }
}

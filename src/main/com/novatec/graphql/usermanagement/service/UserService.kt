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

        val users = userRepository.findAll()
        for (user in users) if (user?.id.equals(id)) return user
        throw Exception("We were unable to find a user with the provided ID")
    }

    fun getUser(): List<User?> {
        return userRepository.findAll()
    }

    fun updateUser(user: User): User? {
        userRepository.save(user)
        return user
    }

    fun createUser(user: User): User {
        val userId = ObjectId.get().toString()
        return userRepository.findById(userId).get()
    }

    fun deleteUser(userId: String) {
        userRepository.deleteById(userId)
    }

    fun checkCredentials(user: User): Boolean{
        return userRepository.findByUsername(user.username).password == user.password
    }
}
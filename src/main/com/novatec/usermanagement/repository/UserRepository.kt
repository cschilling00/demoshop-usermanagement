package src.main.com.novatec.usermanagement.repository

import src.main.com.novatec.usermanagement.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User?, String?> {
    override fun findAll(): List<User?>
    fun findByUsername(name: String): User?
}
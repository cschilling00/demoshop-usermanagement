package src.main.com.novatec.graphql.usermanagement.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
        @Id
        val id: String,
        val username: String,
        val password: String,
        val role: String,
        val email: String?,
        val shippingAdress: List<String?>?,
        val orders: List<String?>?)


enum class Role {
        ROLE_USER, ROLE_ADMIN
}

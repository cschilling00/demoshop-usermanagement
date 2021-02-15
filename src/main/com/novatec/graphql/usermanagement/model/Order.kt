package src.main.com.novatec.graphql.usermanagement.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Order(
        @Id
        val id: String,
        val productIds: List<String?>,
        val user: User,
        val orderDate: String,
        val price: Int)
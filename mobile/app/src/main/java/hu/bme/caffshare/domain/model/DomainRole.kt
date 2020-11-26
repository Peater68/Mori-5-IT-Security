package hu.bme.caffshare.domain.model

import hu.bme.caffshare.data.network.model.UserDTO

enum class DomainRole {
    ADMIN,
    CUSTOMER
}

fun UserDTO.Role.toDomainModel(): DomainRole {
    return when (this) {
        UserDTO.Role.ADMIN -> DomainRole.ADMIN
        UserDTO.Role.CUSTOMER -> DomainRole.CUSTOMER
    }
}
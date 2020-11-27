package hu.bme.caffshare.ui.admin

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.ui.admin.model.User
import hu.bme.caffshare.ui.cafflist.model.CaffFile
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserListPresenter @Inject constructor() {

    suspend fun isUserAdmin(): Boolean = withIOContext {
        delay(10000)
        true
    }

    suspend fun getUsers(): List<User>? = withIOContext {
        listOf(
            User(
                id = "0",
                firstName = "Péter",
                lastName = "Marogna",
                email = "laksjdf@asdf.asdf",
                username = "léaksjdf",
                isBanned = false
            ),
            User(
                id = "1",
                firstName = "Dávid",
                lastName = "Laki",
                email = "laksjdf@asdf.asdf",
                username = "léaksjdf",
                isBanned = false
            ),
            User(
                id = "2",
                firstName = "Béla",
                lastName = "Borsy",
                email = "laksjdf@asdf.asdf",
                username = "léaksjdf",
                isBanned = true
            ),
            User(
                id = "3",
                firstName = "Dávid",
                lastName = "Deák",
                email = "laksjdf@asdf.asdf",
                username = "léaksjdf",
                isBanned = false
            ),
            User(
                id = "4",
                firstName = "Erik",
                lastName = "Kapitány",
                email = "laksjdf@asdf.asdf",
                username = "léaksjdf",
                isBanned = true
            )
        )
    }

    suspend fun deleteUser(user: User): List<User>? = withIOContext {
        listOf(
                User(
                        id = "0",
                        firstName = "Péter",
                        lastName = "Marogna",
                        email = "laksjdf@asdf.asdf",
                        username = "léaksjdf",
                        isBanned = false
                ),
                User(
                        id = "1",
                        firstName = "Dávid",
                        lastName = "Laki",
                        email = "laksjdf@asdf.asdf",
                        username = "léaksjdf",
                        isBanned = false
                ),
                User(
                        id = "2",
                        firstName = "Béla",
                        lastName = "Borsy",
                        email = "laksjdf@asdf.asdf",
                        username = "léaksjdf",
                        isBanned = true
                ),
                User(
                        id = "3",
                        firstName = "Dávid",
                        lastName = "Deák",
                        email = "laksjdf@asdf.asdf",
                        username = "léaksjdf",
                        isBanned = false
                ),
                User(
                        id = "4",
                        firstName = "Erik",
                        lastName = "Kapitány",
                        email = "laksjdf@asdf.asdf",
                        username = "léaksjdf",
                        isBanned = true
                )
        )
    }

    suspend fun makeUserAdmin(user: User): Nothing = withIOContext {
        TODO()
    }

    suspend fun banUser(user: User): Nothing = withIOContext {
        TODO()
    }

}

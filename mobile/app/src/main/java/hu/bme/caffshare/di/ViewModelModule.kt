package hu.bme.caffshare.di

import androidx.lifecycle.ViewModel
import co.zsmb.rainbowcake.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hu.bme.caffshare.ui.admin.UserListViewModel
import hu.bme.caffshare.ui.caffdetails.CaffDetailsViewModel
import hu.bme.caffshare.ui.cafflist.CaffListViewModel
import hu.bme.caffshare.ui.comments.CommentsViewModel
import hu.bme.caffshare.ui.login.LoginViewModel
import hu.bme.caffshare.ui.register.RegisterViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CaffListViewModel::class)
    abstract fun bindBlankViewModel(caffListViewModel: CaffListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CaffDetailsViewModel::class)
    abstract fun bindCaffDetailsViewModel(caffDetailsViewModel: CaffDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    abstract fun bindCommentsViewModel(commentsViewModel: CommentsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    abstract fun bindUserListViewModel(userListViewModel: UserListViewModel): ViewModel
}

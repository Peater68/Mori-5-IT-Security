package hu.bme.caffshare.di

import androidx.lifecycle.ViewModel
import co.zsmb.rainbowcake.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hu.bme.caffshare.ui.admin.UserListViewModel
import hu.bme.caffshare.ui.boughtcaffdetails.BoughtCaffDetailsViewModel
import hu.bme.caffshare.ui.caffdetails.CaffDetailsViewModel
import hu.bme.caffshare.ui.cafflist.CaffListViewModel
import hu.bme.caffshare.ui.comments.CommentsViewModel
import hu.bme.caffshare.ui.login.LoginViewModel
import hu.bme.caffshare.ui.mainactivity.MainViewModel
import hu.bme.caffshare.ui.profile.ProfileViewModel
import hu.bme.caffshare.ui.profile.boughtcafflist.BoughtCaffListViewModel
import hu.bme.caffshare.ui.profile.uploadedcafflist.UploadedCaffListViewModel
import hu.bme.caffshare.ui.register.RegisterViewModel
import hu.bme.caffshare.ui.uploadedcaffdetails.UploadedCaffDetailsViewModel

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

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BoughtCaffListViewModel::class)
    abstract fun bindBoughtCaffListViewModel(boughtCaffListViewModel: BoughtCaffListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UploadedCaffListViewModel::class)
    abstract fun bindUploadedCaffListViewModel(uploadedCaffListViewModel: UploadedCaffListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BoughtCaffDetailsViewModel::class)
    abstract fun bindBoughtCaffDetailsViewModel(boughtCaffDetailsViewModel: BoughtCaffDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UploadedCaffDetailsViewModel::class)
    abstract fun bindUploadedCaffDetailsViewModel(uploadedCaffListViewModel: UploadedCaffDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}

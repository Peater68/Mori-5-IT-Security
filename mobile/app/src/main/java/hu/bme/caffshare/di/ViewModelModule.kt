package hu.bme.caffshare.di

import androidx.lifecycle.ViewModel
import co.zsmb.rainbowcake.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hu.bme.caffshare.ui.cafflist.CaffListViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CaffListViewModel::class)
    abstract fun bindBlankViewModel(caffListViewModel: CaffListViewModel): ViewModel

}

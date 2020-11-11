package hu.bme.caffshare.di

import androidx.lifecycle.ViewModel
import co.zsmb.rainbowcake.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hu.bme.caffshare.ui.blank.BlankViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BlankViewModel::class)
    abstract fun bindBlankViewModel(blankViewModel: BlankViewModel): ViewModel

}

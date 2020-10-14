package com.badcompany.pitak.hilt
//import com.example.benefit.ui.auth.LoginFragmentFactory
import com.badcompany.data.repository.UserRemote
import com.badcompany.remote.UserRemoteImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
abstract class FragmentModule {

//    @JvmStatic
//    @Singleton
//    @Provides
//    @Named("LoginFragmentFactory")
//    fun provideLoginFragmentFactory(): FragmentFactory {
//        return LoginFragmentFactory()
//    }

//    @Binds
//    abstract fun provideUserRepository(userRemoteImpl: UserRemoteImpl): UserRemote

//    @Binds
//    abstract fun providePartnersRepository(partnersRemoteImpl: PartnersRemoteImpl): PartnersRemote


}





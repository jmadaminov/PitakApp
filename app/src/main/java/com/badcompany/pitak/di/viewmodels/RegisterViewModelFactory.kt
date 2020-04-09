package com.badcompany.pitak.di.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.di.register.RegisterScope
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@RegisterScope
class RegisterViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass as Class<ViewModel>]
            ?: creators.entries.firstOrNull { (c, _) -> modelClass.isAssignableFrom(c) }?.value
            ?: throw IllegalArgumentException("Unknown model class $modelClass")
        return creator.get() as T
    }
}


package com.badcompany.pitak.di

import android.R
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

//    @Singleton
//    @Provides
//    @JvmStatic
//    fun provideRequestOptions(): RequestOptions {
//        return RequestOptions
//            .placeholderOf(R.drawable.white_background)
//            .error(R.drawable.white_background)
//    }

//    @JvmStatic
//    @Singleton
//    @Provides
//    fun provideGlideInstance(application: Application?,
//                             requestOptions: RequestOptions?): RequestManager {
//        return Glide.with(application!!)
//            .setDefaultRequestOptions(requestOptions!!)
//    }

//    @JvmStatic
//    @Singleton
//    @Provides
//    fun provideAppDrawable(application: Application?): Drawable {
//        return ContextCompat.getDrawable(application!!, R.drawable.logo)
//    }

}
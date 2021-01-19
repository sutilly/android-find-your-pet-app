package com.example.findmypet.dependencyInjection

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.findmypet.dataAccess.AppExecutors
import com.example.findmypet.dataAccess.LiveAppExecutors
import com.example.findmypet.dataAccess.PetsAndOwnersWebService
import com.example.findmypet.dataAccess.buildWebService
import com.example.findmypet.database.PetsAndOwnersDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.Executors
import javax.inject.Singleton

@HiltAndroidApp
class FindMyPetApp : Application() {}

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PetsAndOwnersDatabase {
        return Room.databaseBuilder(context, PetsAndOwnersDatabase::class.java, "PetsAndOwners")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @InstallIn(ApplicationComponent::class)
    @Module
    abstract class AppModule {
        @Binds
        @Singleton
        abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
    }

    @Provides
    @Singleton
    fun provideWebService(): PetsAndOwnersWebService {
        return buildWebService()
    }

    @Provides
    @Singleton
    fun provideAppExecutors(@ApplicationContext context: Context): AppExecutors {
        return LiveAppExecutors(
            Executors.newSingleThreadExecutor(),
            ContextCompat.getMainExecutor(context)
        )
    }

}
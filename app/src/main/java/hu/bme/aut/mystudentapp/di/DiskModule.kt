package hu.bme.aut.mystudentapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import hu.bme.aut.mystudentapp.data.LocalDatabase
import hu.bme.aut.mystudentapp.data.dataAnnotationObject.CourseDao
import javax.inject.Singleton

@Module
class DiskModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context) : LocalDatabase {
        return Room.databaseBuilder(context, LocalDatabase::class.java, "local.db").build()
    }

    @Provides
    @Singleton
    fun provideCourseDao(localDatabase: LocalDatabase) : CourseDao = localDatabase.courseDao()
}
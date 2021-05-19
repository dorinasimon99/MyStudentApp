package hu.bme.aut.mystudentapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import hu.bme.aut.mystudentapp.data.LocalDatabase
import hu.bme.aut.mystudentapp.data.dataAnnotationObject.CourseDao
import hu.bme.aut.mystudentapp.data.dataAnnotationObject.UserDataDao
import javax.inject.Singleton

@Module
class DiskModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context) : LocalDatabase {
        return Room.databaseBuilder(context, LocalDatabase::class.java, "local.db")
            .addMigrations(migration1_2)
            .addMigrations(migration2_3)
            .addMigrations(migration3_4)
            .build()
    }

    val migration1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `LocalUserData` (`id` TEXT NOT NULL, `userName` TEXT NOT NULL, PRIMARY KEY(`id`))")
        }
    }

    val migration2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE `course` ADD `ownerId` TEXT")
        }
    }

    val migration3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE `course` ADD `teachersList` TEXT")
        }
    }

    @Provides
    @Singleton
    fun provideCourseDao(localDatabase: LocalDatabase) : CourseDao = localDatabase.courseDao()

    @Provides
    @Singleton
    fun provideUserDataDao(localDatabase: LocalDatabase) : UserDataDao = localDatabase.userDao()
}
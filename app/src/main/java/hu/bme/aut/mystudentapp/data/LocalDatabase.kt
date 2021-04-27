package hu.bme.aut.mystudentapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.mystudentapp.data.dataAnnotationObject.CourseDao
import hu.bme.aut.mystudentapp.data.dataAnnotationObject.UserDataDao
import hu.bme.aut.mystudentapp.data.model.LocalCourseEntity
import hu.bme.aut.mystudentapp.data.model.LocalUserData

@Database(entities = [LocalCourseEntity::class, LocalUserData::class], version = 3, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun courseDao() : CourseDao

    abstract fun userDao() : UserDataDao

}

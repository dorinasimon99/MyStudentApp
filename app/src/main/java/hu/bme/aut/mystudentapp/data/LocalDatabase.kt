package hu.bme.aut.mystudentapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import hu.bme.aut.mystudentapp.data.dataAnnotationObject.CourseDao
import hu.bme.aut.mystudentapp.data.dataAnnotationObject.UserDataDao
import hu.bme.aut.mystudentapp.data.model.LocalCourseEntity
import hu.bme.aut.mystudentapp.data.model.LocalUserData

@Database(entities = [LocalCourseEntity::class, LocalUserData::class], version = 4, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun courseDao() : CourseDao

    abstract fun userDao() : UserDataDao

}

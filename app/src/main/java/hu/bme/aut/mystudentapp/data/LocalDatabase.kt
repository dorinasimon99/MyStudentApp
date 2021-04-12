package hu.bme.aut.mystudentapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.mystudentapp.data.dataAnnotationObject.CourseDao
import hu.bme.aut.mystudentapp.data.model.LocalCourseEntity

@Database(entities = [LocalCourseEntity::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun courseDao() : CourseDao

}
package hu.bme.aut.mystudentapp.data.dataAnnotationObject

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.LocalCourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM course WHERE ownerId = :ownerId")
    suspend fun getCoursesFromLocalDb(ownerId: String) : List<LocalCourseEntity>

    @Query("SELECT * FROM course WHERE courseId = :id")
    suspend fun getCourse(id: String) : LocalCourseEntity

    @Insert
    suspend fun insert(course: LocalCourseEntity)

    @Query("DELETE FROM course")
    suspend fun deleteAll()
}
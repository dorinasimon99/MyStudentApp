package hu.bme.aut.mystudentapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course")
data class LocalCourseEntity(
    @ColumnInfo(name = "courseId") @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "courseCode") val courseCode: String,
    @ColumnInfo(name = "courseName") val name: String,
    @ColumnInfo(name = "courseCredits") val credits: Int,
    @ColumnInfo(name = "courseTime") var time: String? = null,
    @ColumnInfo(name = "ownerId") var ownerId: String? = null,
    @ColumnInfo(name = "teachersList") var teachersList: String? = null)
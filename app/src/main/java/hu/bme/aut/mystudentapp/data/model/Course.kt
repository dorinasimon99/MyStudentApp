package hu.bme.aut.mystudentapp.data.model

import com.amplifyframework.datastore.generated.model.CourseData

data class Course(val id: String, val courseCode: String, val name: String, val credits: Int){
    val data : CourseData
        get() = CourseData.builder()
            .courseCode(this.courseCode)
            .name(this.name)
            .credits(this.credits)
            .id(this.id)
            .build()

    companion object{
        fun from(courseData : CourseData) : Course {
            val result = Course(courseData.id, courseData.courseCode, courseData.name, courseData.credits)
            // some additional code will come here later
            return result
        }
    }
}
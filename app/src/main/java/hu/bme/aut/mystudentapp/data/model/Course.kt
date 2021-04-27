package hu.bme.aut.mystudentapp.data.model

import com.amplifyframework.datastore.generated.model.CourseData

data class Course(
    val id: String,
    val courseCode: String,
    val name: String,
    val credits: Int,
    var time: String? = null,
    var users: List<User>? = null,
    var cards: List<Card>? = null,
    var quizzes: List<Quiz>? = null,
    var teachers: List<String>? = null,
    var todos: List<String>? = null){

    val data : CourseData
        get() = CourseData.builder()
            .courseCode(this.courseCode)
            .name(this.name)
            .credits(this.credits)
            .time(this.time)
            .id(this.id)
            .teachers(this.teachers)
            .build()

    companion object{
        fun from(courseData : CourseData) : Course {
            val result = Course(courseData.id, courseData.courseCode, courseData.name, courseData.credits, courseData.time)
            return result
        }
    }
}
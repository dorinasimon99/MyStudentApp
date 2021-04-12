package hu.bme.aut.mystudentapp.data.model

import com.amplifyframework.datastore.generated.model.CourseData
import com.amplifyframework.datastore.generated.model.UserCourse
import com.amplifyframework.datastore.generated.model.UserData

data class UserCourseModel(
    val id: String,
    val user: UserData,
    val course: CourseData
) {
    val data : UserCourse
        get() = UserCourse.builder()
            .user(this.user)
            .course(this.course)
            .id(this.id)
            .build()
}
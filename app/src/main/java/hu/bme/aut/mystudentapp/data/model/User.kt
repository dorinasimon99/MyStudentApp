package hu.bme.aut.mystudentapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amplifyframework.datastore.generated.model.UserData

@Entity(tableName = "user")
data class User(
    @ColumnInfo(name = "userId") @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "userName") var name: String? = null,
    @ColumnInfo(name = "userRole") val role: String,
    @ColumnInfo(name = "userUserName") val username: String,
    @ColumnInfo(name = "userGrade") var grade: Int? = null,
    @ColumnInfo(name = "userCourses") var courses: List<Course>? = null,
    @ColumnInfo(name = "userCards") var cards: List<Card>? = null,
    @ColumnInfo(name = "userQuizzes") var quizzes: List<Quiz>? = null,
    @ColumnInfo(name = "userTeacherRates") var teacherRates: List<TeacherRate>? = null){
    val data : UserData
        get() = UserData.builder()
            .name(this.name)
            .username(this.username)
            .role(this.role)
            .id(this.id)
            .grade(this.grade)
            .build()

    companion object{
        fun from(userData : UserData) : User {
            val courses = arrayListOf<Course>()
            val cards = arrayListOf<Card>()
            val quizzes = arrayListOf<Quiz>()
            for(card in userData.cards){
                cards.add(Card.from(card))
            }
            for(quiz in userData.quizzes){
                quizzes.add(Quiz.from(quiz))
            }
            val result = User(userData.id, userData.name, userData.role, userData.username, userData.grade, courses, cards, quizzes)
            // some additional code will come here later
            return result
        }
    }
}
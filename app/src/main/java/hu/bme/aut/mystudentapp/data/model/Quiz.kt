package hu.bme.aut.mystudentapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amplifyframework.datastore.generated.model.CourseData
import com.amplifyframework.datastore.generated.model.QuizData
import com.amplifyframework.datastore.generated.model.UserData

@Entity(tableName = "quiz")
data class Quiz(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "quizId") val id: String,
    @ColumnInfo(name = "quizOwner") val owner: UserData,
    @ColumnInfo(name = "quizCourse") val course: CourseData,
    @ColumnInfo(name = "quizQuestions") var questions: List<String>?,
    @ColumnInfo(name = "quizAnswers") var answers: List<String>?
) {
    val data : QuizData
        get() = QuizData.builder()
            .owner(this.owner)
            .course(this.course)
            .id(this.id)
            .quizAnswers(this.answers)
            .quizQuestions(this.questions)
            .build()

    companion object{
        fun from(quizData : QuizData) : Quiz {
            val questions = arrayListOf<String>()
            val answers = arrayListOf<String>()
            for(question in quizData.quizQuestions){
                questions.add(question)
            }
            for(answer in quizData.quizAnswers){
                answers.add(answer)
            }
            val result = Quiz(quizData.id, quizData.owner, quizData.course, questions, answers)
            return result
        }
    }
}
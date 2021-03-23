package hu.bme.aut.mystudentapp.data.model

import com.amplifyframework.datastore.generated.model.CourseData
import com.amplifyframework.datastore.generated.model.QuizData
import com.amplifyframework.datastore.generated.model.UserData

data class Quiz(
    val id: String,
    val owner: UserData,
    val course: CourseData,
    var questions: List<String>?,
    var answers: List<String>?
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
package hu.bme.aut.mystudentapp.data.model

import com.amplifyframework.datastore.generated.model.CardData
import com.amplifyframework.datastore.generated.model.CourseData
import com.amplifyframework.datastore.generated.model.UserData

data class Card(
    val id: String,
    val owner: UserData,
    val course: CourseData,
    var questions: List<String>?,
    var answers: List<String>?){

    val data : CardData
        get() = CardData.builder()
            .id(this.id)
            .cardQuestions(this.questions)
            .cardAnswers(this.answers)
            .course(this.course)
            .owner(this.owner)
            .build()

    companion object{
        fun from(cardData : CardData) : Card {
            val questions = arrayListOf<String>()
            val answers = arrayListOf<String>()
            for(question in cardData.cardQuestions){
                questions.add(question)
            }
            for(answer in cardData.cardAnswers){
                answers.add(answer)
            }
            val result = Card(cardData.id, cardData.owner, cardData.course, questions, answers)
            // some additional code will come here later
            return result
        }
    }
}
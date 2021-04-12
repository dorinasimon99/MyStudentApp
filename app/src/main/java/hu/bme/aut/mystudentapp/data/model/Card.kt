package hu.bme.aut.mystudentapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amplifyframework.datastore.generated.model.CardData
import com.amplifyframework.datastore.generated.model.CourseData
import com.amplifyframework.datastore.generated.model.UserData

@Entity(tableName = "card")
data class Card(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "cardId") val id: String,
    @ColumnInfo(name = "cardOwner") val owner: UserData,
    @ColumnInfo(name = "cardCourse") val course: CourseData,
    @ColumnInfo(name = "cardQuestions") var questions: List<String>?,
    @ColumnInfo(name = "cardAnswers") var answers: List<String>?){

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
package hu.bme.aut.mystudentapp.data.model

import com.amplifyframework.datastore.generated.model.UserData

data class User(
    val id: String,
    val name: String?,
    val role: String,
    val grade: Int?,
    var courses: List<Course>?,
    var cards: List<Card>?,
    var quizzes: List<Quiz>?){
    val data : UserData
        get() = UserData.builder()
            .role(this.role)
            .id(this.id)
            .name(this.name)
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
            val result = User(userData.id, userData.name, userData.role, userData.grade, courses, cards, quizzes)
            // some additional code will come here later
            return result
        }
    }
}
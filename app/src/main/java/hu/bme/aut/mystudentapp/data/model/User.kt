package hu.bme.aut.mystudentapp.data.model

import com.amplifyframework.datastore.generated.model.UserData

data class User(val id: String, val name: String?, val role: String, val grade: Int?){
    val data : UserData
        get() = UserData.builder()
            .role(this.role)
            .id(this.id)
            .name(this.name)
            .grade(this.grade)
            .build()

    companion object{
        fun from(userData : UserData) : User {
            val result = User(userData.id, userData.name, userData.role, userData.grade)
            // some additional code will come here later
            return result
        }
    }
}
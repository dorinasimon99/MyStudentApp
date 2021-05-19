package hu.bme.aut.mystudentapp.data.model

import com.amplifyframework.datastore.generated.model.TodoData

data class Todo(
    val id: String,
    val name: String,
    val done: Boolean,
    val ownerId: String,
    val courseId: String
){
    val data : TodoData
    get() = TodoData.builder()
        .name(this.name)
        .done(this.done)
        .ownerId(this.ownerId)
        .courseId(this.courseId)
        .id(this.id)
        .build()

    companion object {
        fun from(todoData: TodoData) : Todo {
            return Todo(todoData.id, todoData.name, todoData.done, todoData.ownerId, todoData.courseId)
        }
    }
}
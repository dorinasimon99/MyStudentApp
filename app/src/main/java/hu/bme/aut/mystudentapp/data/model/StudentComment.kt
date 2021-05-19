package hu.bme.aut.mystudentapp.data.model

import com.amplifyframework.datastore.generated.model.CommentData

data class StudentComment(
    val id: String,
    val name: String? = "Anonymous",
    val comment: String,
    val courseName: String,
    val teacherName: String
){
    val data : CommentData
    get() = CommentData.builder()
        .teachername(this.teacherName)
        .comment(this.comment)
        .coursename(this.courseName)
        .id(this.id)
        .name(this.name)
        .build()

    companion object{
        fun from(commentData: CommentData) : StudentComment {
            return StudentComment(commentData.id, commentData.name, commentData.comment, commentData.coursename, commentData.teachername)
        }
    }
}
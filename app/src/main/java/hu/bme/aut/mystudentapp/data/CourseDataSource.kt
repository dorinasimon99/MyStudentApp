package hu.bme.aut.mystudentapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.mystudentapp.data.model.Course
import javax.inject.Inject

class CourseDataSource @Inject constructor(){

    private val _courses = MutableLiveData<MutableList<Course>>(mutableListOf())
    private val TAG = "CourseDataSource"

    suspend fun courses() : LiveData<MutableList<Course>> = _courses
    suspend fun addCourse(c : Course){
        val courses = _courses.value
        if(courses != null){
            courses.add(c)
            _courses.notifyObserver()
        } else {
            Log.e(TAG, "addCourse : course collection is null !!")
        }
    }
    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.postValue(this.value)
    }
    fun notifyObserver() {
        _courses.notifyObserver()
    }
}
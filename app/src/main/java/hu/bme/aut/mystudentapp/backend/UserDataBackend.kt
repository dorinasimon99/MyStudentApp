package hu.bme.aut.mystudentapp.backend

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.UserData
import hu.bme.aut.mystudentapp.data.model.User

object UserDataBackend {

    private val _isSignedIn = MutableLiveData<Boolean>()
    private val _userData = MutableLiveData<User>()
    var isSignedIn : LiveData<Boolean> = _isSignedIn

    fun setSignedIn(newValue: Boolean) {
        _isSignedIn.postValue(newValue)
    }

    var hasUserData : LiveData<User> = _userData
    fun getUserData() : User? {
        var user : User?
        Log.i("UserDataBackend", "getUserData _userdata: ${_userData.value}")
        if(_userData.value == null){
            Amplify.API.query(
                ModelQuery.list(UserData::class.java),
                { response ->
                    Log.i("UserDataBackend", "Queried")
                    for (userData in response.data) {
                        user = User.from(userData)
                        _userData.postValue(user)
                    }
                },
                { error -> Log.e("UserDataBackend", "Query failure:", error) }
            )
        }
        return _userData.value
    }
    fun setUserData(user: User) {
        Log.i("UserDataBackend", "Creating user")
        Log.i("UserDataBackend", "Creating user user: $user")

        Amplify.API.mutate(
            ModelMutation.create(user.data),
            { response ->
                Log.i("UserDataBackend", "Created")
                if (response.hasErrors()) {
                    Log.e("UserDataBackend", response.errors.first().message)
                } else {
                    Log.i("UserDataBackend", "Created User data: ${response.data}")
                    _userData.postValue(User.from(response.data))
                }
            },
            { error -> Log.e("UserDataBackend", "Create failed:", error) }
        )
    }
}
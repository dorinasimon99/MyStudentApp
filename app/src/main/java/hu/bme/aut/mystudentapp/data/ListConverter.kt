package hu.bme.aut.mystudentapp.data

import androidx.room.TypeConverter

class ListConverter {
    @TypeConverter
    fun stringToList(stringListString: String?): List<String>? {
        if(stringListString == null){ return null }
        return stringListString.split(";").map { it }
    }

    @TypeConverter
    fun listToString(stringList: List<String>?): String? {
        if(stringList == null){ return null }
        return stringList.joinToString(separator = ";")
    }
}
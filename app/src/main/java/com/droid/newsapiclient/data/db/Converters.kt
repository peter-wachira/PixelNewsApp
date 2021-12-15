package com.droid.newsapiclient.data.db

import androidx.room.TypeConverter
import com.droid.newsapiclient.data.model.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String{
        return  source.name
    }
    @TypeConverter
    fun toSource(name: String):Source{
        return Source(name,name)
    }
}
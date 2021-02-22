package com.paypay.currencyconverter.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonElement

class TypeConverter {

    @TypeConverter
    fun convertFromJsonElementToString(jsonElement: JsonElement): String {
        return Gson().toJson(jsonElement)
    }

    @TypeConverter
    fun convertFromStringToJsonElement(stringJson: String): JsonElement {
        return Gson().fromJson(stringJson, JsonElement::class.java)
    }
}

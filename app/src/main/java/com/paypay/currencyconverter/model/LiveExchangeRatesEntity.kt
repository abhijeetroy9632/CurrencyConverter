package com.paypay.currencyconverter.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "LiveExchangeRatesTable")
data class LiveExchangeRatesEntity (
    @PrimaryKey
    @Expose
    @SerializedName("success") val success : Boolean,
    @Expose
    @SerializedName("terms") val terms : String,
    @Expose
    @SerializedName("privacy") val privacy : String,
    @Expose
    @SerializedName("timestamp") val timestamp : Long,
    @Expose
    @SerializedName("source") val source : String,
    @Expose
    @SerializedName("quotes") val quotes : JsonElement
)
package com.hofit.hofitcellcomtest.repository.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegionalBloc {
    @SerializedName("acronym")
    @Expose
    var acronym: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("otherAcronyms")
    @Expose
    var otherAcronyms: List<String>? = null

    @SerializedName("otherNames")
    @Expose
    var otherNames: List<String>? = null

}
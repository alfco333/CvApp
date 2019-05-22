package com.angelvargas.data.mappers

import com.angelvargas.data.models.Profiles
import com.angelvargas.data.models.database.RealmProfile

class RealmProfileMapper: Transform<Profiles, RealmProfile>() {

    override fun transform(value: Profiles): RealmProfile {
         return RealmProfile(value.network ?: "", value.username ?: "", value.url ?: "")
    }
}
package com.angelvargas.data.database

import io.realm.Realm

interface DatabaseProvider {
    val instance: Realm
}
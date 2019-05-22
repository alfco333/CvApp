package com.angelvargas.data.database

import io.realm.Realm

class RealmProvider: DatabaseProvider {
    override val instance: Realm
        get() = Realm.getDefaultInstance()
}
package com.obolonnyy.hacktest

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by Vova on 01.04.2018.
 */
open class Participant (
        @PrimaryKey var id: String = UUID.randomUUID().toString(),
        var firstName: String = "",
        var lastName: String = "",
        var middleName: String = "",
        var group: String = "",
        var photopath: String = "",
        var description: String = ""
) : RealmObject()
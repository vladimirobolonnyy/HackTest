package com.obolonnyy.hacktest

import android.content.Context
import io.realm.Realm
import io.realm.kotlin.where


/**
 * Created by max on 02.04.2018.
 */

object ParticipantDatabaseService {

    fun init(context: Context) {
        Realm.init(context)
    }

    fun prepopulateParticipantsIfFirstRun(context: Context) {
        val appSettings = context.getSharedPreferences("BMSTU_PARTICIPANTS", Context.MODE_PRIVATE)
        val isFirstRun = appSettings.getBoolean("IS_FIRST_RUN", true)
        if (isFirstRun) {
            prepopulateParticipants()
            val settingsEditor = appSettings.edit()
            settingsEditor.putBoolean("IS_FIRST_RUN", false)
            settingsEditor.commit()
        }
    }

    private fun prepopulateParticipants() {

        val mihanImagePath = "android.resource://com.obolonnyy.hacktest/"+R.mipmap.mihan
        val maxImagePath = "android.resource://com.obolonnyy.hacktest/"+R.mipmap.max
        val initialGang = listOf(
                Participant(firstName = "Максим", lastName = "Хабрат", middleName = "Дмитриевич",
                        group = "ИУ3-48м", photopath = maxImagePath, description = "Крутой парень"),
                Participant(firstName = "Владимир", lastName =  "Оболонный", middleName = "Игоревич",
                        group = "ИУ3-47м", photopath = "", description = "Крутой парень"),
                Participant(firstName = "Михаил", lastName = "Яковенко", middleName =  "Андреевич",
                        group = "ИУ3-48м", photopath = mihanImagePath, description = "Крутой парень"),
                Participant(firstName = "Евгений", lastName = "Саневич",  middleName = "Григорьевич",
                        group = "ИУ3-48м", photopath = "", description = "Крутой парень")
        )
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealm(initialGang)
        realm.commitTransaction()

    }

    fun addParticipant(p: Participant) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealm(p)
        realm.commitTransaction()
    }

    fun removeParticipant(p: Participant) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        p.deleteFromRealm()
        realm.commitTransaction()
    }

    fun getAllParticipants() : List<Participant> {
        val realm = Realm.getDefaultInstance()
        val query = realm.where<Participant>()
        return query.findAll()
    }
}

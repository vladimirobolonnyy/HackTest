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

        val mihanImagePath = "android.resource://com.obolonnyy.hacktest/"+ R.mipmap.mihan
        val vovanImagePath = "android.resource://com.obolonnyy.hacktest/"+ R.mipmap.vovan
        val maxanImagePath = "android.resource://com.obolonnyy.hacktest/"+ R.mipmap.maxan
        val jeganImagePath = "android.resource://com.obolonnyy.hacktest/"+ R.mipmap.jegan
        val initialGang = listOf(
                Participant(firstName = "Максим", lastName = "Хабрат", middleName = "Дмитриевич",
                        group = "ИУ3-48м", photopath = maxanImagePath, description = "Крутой парень"),
                Participant(firstName = "Владимир", lastName =  "Оболонный", middleName = "Игоревич",
                        group = "ИУ3-47м", photopath = vovanImagePath, description = "Крутой парень"),
                Participant(firstName = "Михаил", lastName = "Яковенко", middleName =  "Андреевич",
                        group = "ИУ3-48м", photopath = mihanImagePath, description = "Крутой парень"),
                Participant(firstName = "Евгений", lastName = "Саневич",  middleName = "Григорьевич",
                        group = "ИУ3-48м", photopath = jeganImagePath, description = "Крутой парень")
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
        Realm.getDefaultInstance().executeTransaction { realm ->
            realm.where<Participant>().equalTo("id", p.id).findFirst()?.deleteFromRealm()
        }
    }

    fun getAllParticipants() : List<Participant> {
        val realm = Realm.getDefaultInstance()
        val query = realm.where<Participant>()
        return query.findAll()
    }
}

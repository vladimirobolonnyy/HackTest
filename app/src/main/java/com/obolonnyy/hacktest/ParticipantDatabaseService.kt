package com.obolonnyy.hacktest

import android.content.Context
import io.realm.Realm
import io.realm.kotlin.where


/**
 * Обертка над объектом БД Realm/ Инкапсулирует в себе всю логику по работе с БД участников
 *
 * Created by max on 02.04.2018.
 */

object ParticipantDatabaseService {

    /**
     * Инифиализация БД
     */
    fun init(context: Context) {
        Realm.init(context)
    }

    /**
     * Добавляет в БД инфо о начальном составе команды, если приложенике запускается один раз.
     */
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

    /**
     * Добавление в БД информации начальном составе команды
     */
    private fun prepopulateParticipants() {
        val mihanImagePath = "android.resource://com.obolonnyy.hacktest/"+ R.mipmap.mihan
        val vovanImagePath = "android.resource://com.obolonnyy.hacktest/"+ R.mipmap.vovan
        val maxanImagePath = "android.resource://com.obolonnyy.hacktest/"+ R.mipmap.maxan
        val jeganImagePath = "android.resource://com.obolonnyy.hacktest/"+ R.mipmap.jegan
        val initialGang = listOf(
                Participant(firstName = "Максим", lastName = "Хабрат", middleName = "Дмитриевич",
                        group = "ИУ3-48м", photopath = maxanImagePath,
                        description = "Возраст 23 года. Младший инженер-разработчик в компании КРОК. " +
                                "За полтора года опыта работы успел попробовать себя в бекэнде, фронтенде, " +
                                "и разработке смарт-контрактов для Ethereum."),
                Participant(firstName = "Владимир", lastName =  "Оболонный", middleName = "Игоревич",
                        group = "ИУ3-47м", photopath = vovanImagePath,
                        description = "Возраст 23 года. Работает Android-разраюотчиком в Сбертехе," +
                                " а потому - главный Android-специалист команды. В свободное от " +
                                "работы время изучает Data Science."),
                Participant(firstName = "Михаил", lastName = "Яковенко", middleName =  "Андреевич",
                        group = "ИУ3-48м", photopath = mihanImagePath,
                        description = "Возраст 24 года. Старший инженер-рабзработчик Сбертеха. " +
                                "Специалист по веб-приложениям, а также методологиям разработки Agile и DevOps. " +
                                "Не чуждается таких слов, как Gridgain, Blockcjain, Ansible и т.д."),
                Participant(firstName = "Евгений", lastName = "Саневич",  middleName = "Григорьевич",
                        group = "ИУ3-48м", photopath = jeganImagePath,
                        description = "Возраст 22 года. Работал в TS Consulting, а сейчас работает в НСПК, " +
                                "а потому отлично разбирается в работе высоконагруженных систем, и " +
                                "знает, как должно выглядеть грамотное многопоточное приложение.")
        )
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealm(initialGang)
        realm.commitTransaction()

    }

    /**
     * Добавляет в БД нового участника
     */
    fun addParticipant(p: Participant) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealm(p)
        realm.commitTransaction()
    }

    /**
     * Удаляет участника из БД
     */
    fun removeParticipant(p: Participant) {
        Realm.getDefaultInstance().executeTransaction { realm ->
            realm.where<Participant>().equalTo("id", p.id).findFirst()?.deleteFromRealm()
        }
    }

    /**
     * Возвращает из БД всех имеющихя участников
     */
    fun getAllParticipants() : List<Participant> {
        val realm = Realm.getDefaultInstance()
        val query = realm.where<Participant>()
        return query.findAll()
    }
}

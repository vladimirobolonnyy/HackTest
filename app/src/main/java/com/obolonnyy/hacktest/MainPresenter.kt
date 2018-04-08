package com.obolonnyy.hacktest

import android.widget.Toast

/**
 * Содержит реализацию основных функций приложения.
 *
 * Created by Vladimir Obolonnyy on 02.04.2018.
 */
class MainPresenter(val view: MainView) {

    companion object {
        const val fieldsError = "Заполните как минимум имя и фамилию участника"
    }

    /**
     * Отвечает за создание и обновление содержимого списка
     */
    fun initMainAdapterItems() {
        view.initRecyclerView(ParticipantDatabaseService.getAllParticipants())
    }

    /**
     * Удаляет указанного участника из БД участников, выводит сообщение о проведенной операции
     * и обновляет список
     */
    fun removeParticipant(participant: Participant) {
        val name = participant.firstName
        val lastName = participant.lastName
        ParticipantDatabaseService.removeParticipant(participant)
        view.makeToast("$name $lastName удалён.", Toast.LENGTH_SHORT)
        initMainAdapterItems()
        view.onBackPressed()
    }

    /**
     * Добавляет участника в БД участников, если указаны имя и фамилия участника, и обновляет список.
     * Иначе выводит сообщение об ошибке
     */
    fun addParticipant(participant: Participant) {
        if (participant.firstName.isNotEmpty() && participant.lastName.isNotEmpty()) {
            view.hideKeyboard()
            ParticipantDatabaseService.addParticipant(participant)
            view.makeToast("${participant.firstName} ${participant.lastName} добавлен.", Toast.LENGTH_SHORT)
            initMainAdapterItems()
            view.onBackPressed()
        } else {
            view.makeToast(fieldsError, Toast.LENGTH_SHORT)
        }
    }
}
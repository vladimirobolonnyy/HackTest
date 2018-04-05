package com.obolonnyy.hacktest

import io.realm.Realm

/**
 * Created by Vladimir Obolonnyy on 02.04.2018.
 */
class MainPresenter(val view: MainView) {
    // Тут должна быть вся логика работы приложения

    fun initAllItems() {
        view.initRecyclerView(ParticipantDatabaseService.getAllParticipants())
        //view.initRecyclerView(database.allParticipants)
    }

    fun generateUs(): ArrayList<Participant> {

        //ToDo заменить эту функцию на возвращение элементов из базы данных
        val result = ArrayList<Participant>()
        result.add(Participant("Владимир Оболонный"))
        result.add(Participant("Максим Хабрат"))
        result.add(Participant("Михаил", "Яковенко", "Андреевич",
                "M", "Group?", "reference",
                "Самый лучший разраб в мире"))
        result.add(Participant("Евгений Саневич"))
        return result
    }
}
package com.obolonnyy.hacktest

/**
 * Created by Vladimir Obolonnyy on 02.04.2018.
 */
class MainPresenter(val view: MainView) {
    // Тут должна быть вся логика работы приложения

    fun initAllItems() {
        view.initRecyclerView(ParticipantDatabaseService.getAllParticipants())
    }

}
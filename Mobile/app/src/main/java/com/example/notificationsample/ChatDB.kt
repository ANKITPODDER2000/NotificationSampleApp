package com.example.notificationsample

import androidx.core.app.Person

object ChatDB {
    private val mapDB: HashMap<String, ArrayList<Pair<Person, String>>> = hashMapOf()

    fun getChat(person: Person): ArrayList<Pair<Person, String>>? {
        return mapDB[person.name]
    }

    fun storeNewChat(person: Person, chat: Pair<Person, String>) {
        if (!mapDB.contains(person.name)) mapDB[person.name.toString()] = arrayListOf()
        mapDB[person.name]?.add(chat)
    }

}
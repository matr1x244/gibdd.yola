package com.geekbrains.gibddyola.game.domain

import android.content.Context
import androidx.room.Room

object QuestionDatabaseBuilder {
    private var instance: QuestionDatabase? = null

    fun getInstance(context: Context): QuestionDatabase {
        if (instance == null) {
            synchronized(QuestionDatabase::class) {
                instance = buildRoomDB(context)
            }
        }
        return instance!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            QuestionDatabase::class.java,
            "game_questions"
        ).build()
}
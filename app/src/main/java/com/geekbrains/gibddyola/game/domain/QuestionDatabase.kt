package com.geekbrains.gibddyola.game.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.geekbrains.gibddyola.game.domain.entity.AnswersConverter
import com.geekbrains.gibddyola.game.domain.entity.QuestionDomain

@Database(entities = [QuestionDomain::class], version = 1, exportSchema = true)
@TypeConverters(AnswersConverter::class)
abstract class QuestionDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}

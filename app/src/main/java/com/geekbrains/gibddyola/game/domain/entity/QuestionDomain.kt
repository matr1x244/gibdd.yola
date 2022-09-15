package com.geekbrains.gibddyola.game.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionDomain(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "question") val question: String,
    @ColumnInfo(name = "answers") val answers: List<Pair<String, Boolean>>,
    @ColumnInfo(name = "explanation") val answer_about: String
)
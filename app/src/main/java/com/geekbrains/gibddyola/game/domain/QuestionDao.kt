package com.geekbrains.gibddyola.game.domain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.geekbrains.gibddyola.game.domain.entity.QuestionDomain

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questionDomain")
    suspend fun getAll(): List<QuestionDomain>

    @Query("SELECT * FROM questionDomain WHERE id LIKE :id")
    suspend fun get(id: String): QuestionDomain

    @Insert
    suspend fun insertAll(questions: List<QuestionDomain>)

    @Insert
    suspend fun insert(question: QuestionDomain)

    @Delete
    suspend fun delete(question: QuestionDomain)
}
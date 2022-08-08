package com.geekbrains.gibddyola.data

import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.domain.employee.EntityAvarkom
import com.geekbrains.gibddyola.domain.employee.Repository

class LocalRepositoryImpl : Repository {

    override fun getListAvarkom(): List<EntityAvarkom> {
        return listOf(
            EntityAvarkom(
                0,
                "Лежнин Евгений",
                "Аварийный комиссар. Аварийный комиссар (аварком) — специалист по оценке причиненного ущерба, причин страхового случая и др.",
                "Оформляю ДТП уже около 10 лет",
                R.mipmap.av_ap
            ),
            EntityAvarkom(
                1,
                "Алексей Бетёв",
                "Аварийный комиссар – эксперт, который оказывает услуги правильного оформления документов,фиксации условий происшествия и оценки урона.",
                "Оформляю ДТП уже около 7 лет",
                R.mipmap.av_al
            )
        )
    }

}
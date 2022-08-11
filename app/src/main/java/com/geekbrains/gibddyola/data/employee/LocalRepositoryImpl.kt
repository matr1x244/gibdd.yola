package com.geekbrains.gibddyola.data.employee

import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.domain.employee.EntityAvarkom
import com.geekbrains.gibddyola.domain.employee.Repository

class LocalRepositoryImpl : Repository {

    private val yearJob = "Опыт работы с"

    override fun getListAvarkom(): List<EntityAvarkom> {
        return listOf(
            EntityAvarkom(
                0,
                "Пахмутов Виктор",
                "Аварийный комиссар",
                "$yearJob 2020 года",
                R.mipmap.av_ap
            ),
            EntityAvarkom(
                1,
                "Алексей Бетёв",
                "Аварийный комиссар",
                "$yearJob 2017 года",
                R.mipmap.av_al
            ),
            EntityAvarkom(
                2,
                "Лежнин Евгений",
                "Аварийный комиссар",
                "$yearJob 2016 года",
                R.mipmap.av_ae
            ),
            EntityAvarkom(
                3,
                "Смирнов Дмитрий",
                "Аварийный комиссар",
                "$yearJob 2005 года",
                R.mipmap.av_ac

            ),
            EntityAvarkom(
                4,
                "Яблоновский Александр",
                "Аварийный комиссар",
                "$yearJob 2003 года",
                R.mipmap.av_aya
            )
        )
    }

}
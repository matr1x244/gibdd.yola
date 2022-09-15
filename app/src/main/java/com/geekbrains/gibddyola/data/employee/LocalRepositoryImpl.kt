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
                "Опыт работы: С 2005 года\n\nИнтересы, увлечения: автокроссовые гонки, профессиональные участия в дрифт заездах.\n\nДмитрий - это яркий пример ответственного подхода к делу. Человек который ответственно подходит к каждому водителю, уделяя внимание всем мелочам, всё больше и больше укрепляя свой авторитет множеством положительных отзывов от водителей.\n\nКлючом к успеху разрешения спорных ситуаций на месте дорожно-транспортного происшествия считает индивидуальный подход к каждому участнику дорожно-транспортного происшествия. Честность и ответственность - приоритетные качества Дмитрия.",
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
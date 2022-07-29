package com.geekbrains.gibddyola.data

import com.geekbrains.gibddyola.domain.EntityAvarkom
import com.geekbrains.gibddyola.domain.Repository

class LocalRepositoryImpl : Repository {

    override fun getListAvarkom(): List<EntityAvarkom> {
        return listOf(
            EntityAvarkom(
                0,
                "Лежнин Евгений",
                "Аварийный комиссар. Аварийный комиссар (аварком) — специалист по оценке причиненного ущерба, причин страхового случая и др., который по поручению страховщика занимается определением причин и характера события, имеющего признаки страхового случая, а также размера причиненных им убытков.",
                "Оформляю ДТП уже около 10 лет",
                "---"
            ),
            EntityAvarkom(
                1,
                "Алексей Бетёв",
                "Аварийный комиссар – эксперт, который оказывает услуги правильного оформления документов, фиксации условий происшествия и оценки урона. Аварийный комиссар может быть оформлен как юр.лицо либо индивидуальный предприниматель. Лицензирование такого типа профессии необязательно. Помимо вышеописанных услуг, аварийный комиссар также может предоставить услуги правового характера, так как он чаще всего имеет соответствующие навыки решения вопросов подобного характера.",
                "Оформляю ДТП уже около 7 лет",
                "---"
            )
        )
    }

}
package com.geekbrains.gibddyola.data

import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.domain.game.Question
import com.geekbrains.gibddyola.domain.game.RepositoryGame

class LocalRepositoryGameImpl: RepositoryGame {

    override fun getQuestions(): ArrayList<Question> {
        val questions = ArrayList<Question>()

        questions.add(
            Question(
                0,
                "Где начинают действовать требования Правил, относящиеся к населенным пунктам?",
                R.mipmap.test,
                "Только с места установки дорожного знака «Начало населенного пункта» на белом фоне.",
                "С места установки дорожного знака с названием населенного пункта на белом или синем фоне",
                "В начале застроенной территории, непосредственно прилегающей к дороге.",
                null,
                "В начале застроенной территории, непосредственно прилегающей к дороге."
            )
        )
        questions.add(
            Question(
                1,
                "Допускается ли движение автомобилей по тротуарам или пешеходным дорожкам?",
                null,
                "Допускается.",
                "Допускается только при доставке грузов к торговым и другим предприятиям, расположенным непосредственно у тротуаров или пешеходных дорожек, если отсутствуют другие возможности подъезда",
                "Не допускается",
                null,
                "Допускается только при доставке грузов к торговым и другим предприятиям, расположенным непосредственно у тротуаров или пешеходных дорожек, если отсутствуют другие возможности подъезда"
            )
        )

        questions.add(
            Question(
                2,
                "Кому Вы обязаны уступить дорогу при движении прямо?",
                R.drawable.ic_car_wash,
                "Только мотоциклу",
                "Мотоциклу и легковому автомобилю",
                "Автобусу и мотоциклу",
                "Всем транспортным средствам",
                "Всем транспортным средствам"
            )
        )

        questions.add(
            Question(
                3,
                "Разрешен ли Вам въезд на железнодорожный переезд в данной ситуации?",
                R.drawable.ic_car_wash,
                "Разрешен",
                "Разрешен, если отсутствует приближающийся поезд.",
                "Запрещен",
                null,
                "Запрещен"
            )
        )
        questions.add(
            Question(
                4,
                "При движении в условиях недостаточной видимости можно использовать противотуманные фары:",
                R.drawable.ic_car_wash,
                "Только отдельно от ближнего или дальнего света фар",
                "Только совместно с ближним или дальним светом фар",
                "Как отдельно, так и совместно с ближним или дальним светом фар",
                null,
                "Только совместно с ближним или дальним светом фар"
            )
        )

        return questions
    }
}
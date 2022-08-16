package com.geekbrains.gibddyola.game.data

import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.game.domain.QuestionRepository
import com.geekbrains.gibddyola.game.domain.entity.QuestionDomain

class QuestionRepositoryImpl: QuestionRepository {
    override fun getAllQuestions() = listOf(
        QuestionDomain(
            1,
            R.mipmap.b26_1,
            "Выезд из двора или c другой прилегающей территории:",
            listOf(
                Pair("Считается перекрестком равнозначных дорог", false),
                Pair("Считается перекрестком неравнозначных дорог", false),
                Pair("Не считается перекрестком", true)
            ),
            "Согласно определению понятия «перекресток», выезд с прилегающей территории не считается перекрестком п 1.2. Сюда относятся выезды из дворов, жилых массивов, автостоянок, АЗС, предприятий и другие подобные ситуации, когда водитель должен уступить дорогу транспортным средствам и пешеходам, движущимся по дороге."
        ),
        QuestionDomain(
            2,
            R.mipmap.b26_2,
            "Какие из указанных знаков требуют обязательной остановки?",
            listOf(
                Pair("Только А", false),
                Pair("Только Б", true),
                Pair("Б и В", false),
                Pair("Все", false)
            ),
            "Только знак Б (2.5 «Движение без остановки запрещено») обязывает водителей остановиться у стоп-линий, а при ее отсутствии - перед краем пересекаемой проезжей части, на железнодорожном переезде, перед знаком. Знак В (6.16 «Стоп-линия») показывает лишь место остановки транспортных средств при запрещающем сигнале светофора или регулировщика. Знаки Г (2.4 «Уступите дорогу») и А (2.6 «Преимущество встречного движения») обязывают водителя уступить дорогу, при необходимости, соответственно на пересечении дорог или ее узком участке."
        ),
        QuestionDomain(
            3,
            R.mipmap.b26_3,
            "В зоне действия этого знака разрешается использовать звуковой сигнал:",
            listOf(
                Pair("Только для предупреждения об обгоне", false),
                Pair("Только для предотвращения дорожно-транспортного происшествия", true),
                Pair("В обоих перечисленных случаях", false)
            ),
            "Знак 3.26 «Подача звукового сигнала запрещена» допускает подачу сигнала только в одном случае - для предотвращения дорожно-транспортного происшествия. Вне населенных пунктов, где нет этого знака, звуковой сигнал можно использовать также для предупреждения других водителей об обгоне."
        ),
        QuestionDomain(
            4,
            R.mipmap.none_image_question,
            "Где начинают действовать требования Правил, относящиеся к населенным пунктам?",
            listOf(
                Pair(
                    "Только с места установки дорожного знака «Начало населенного пункта» на белом фоне",
                    true
                ),
                Pair(
                    "С места установки дорожного знака с названием населенного пункта на белом или синем фоне",
                    false
                ),
                Pair("В начале застроенной территории, непосредственно прилегающей к дороге", false)
            ),
            "Правила устанавливают ряд требований, которые обязательны для выполнения только в населенных пунктах: скорость движения - до 60 км/ч, возможность остановки на левой стороне дороги, запрещение звукового сигнала и т.д. Эти требования начинают действовать только с места установки знака с названием населенного пункта на белом фоне, т.е. знака 5.23.1 Название населённого пункта и 5.23.2 «Начало населенного пункта»."
        ),
        QuestionDomain(
            5,
            R.mipmap.b26_5,
            "Такой вертикальной разметкой обозначают боковые поверхности ограждений",
            listOf(
                Pair("Только на опасных участках дорог", false),
                Pair("Только на участках дорог, не относящихся к опасным", true),
                Pair("На всех участках дорог", false)
            ),
            "Такой вертикальной разметкой 2.6 обозначают боковые поверхности дорожных ограждений во всех других случаях, когда не применяется разметка 2.5, используемая только на опасных участках."
        ),
        QuestionDomain(
            6,
            R.mipmap.b14_6,
            "Должны ли Вы остановиться по требованию регулировщика в указанном им месте?",
            listOf(
                Pair("Должны", true),
                Pair("Должны только с заездом на тротуар", false),
                Pair("Не должны", false)
            ),
            "Вы должны остановиться у тротуара, так как водители обязаны выполнять распоряжения регулировщика даже в тех случаях, когда они противоречат требованиям дорожных знаков и разметки п 6.11"
        ),
        QuestionDomain(
            7,
            R.mipmap.b14_7,
            "Такой сигнал рукой, подаваемый водителем мотоцикла, информирует Вас:",
            listOf(
                Pair("О его намерении повернуть налево или выполнить разворот", true),
                Pair("О его намерении продолжить движение прямо или налево", false),
                Pair("О наличии транспортного средства, приближающегося слева", false)
            ),
            "При отсутствии или неисправности световых указателей поворота их заменяют подачей сигнала рукой. Данный сигнал мотоциклиста (вытянутая в сторону левая рука) соответствует сигналу левого поворота (или разворота) п 8.1"
        ),
        QuestionDomain(
            8,
            R.mipmap.b14_8,
            "По какой траектории Вам разрешается выполнить поворот налево?",
            listOf(
                Pair("Только по А", false),
                Pair("Только по Б", false),
                Pair("По любой из указанных", true)
            ),
            "Знак 5.7.2 «Выезд на дорогу с односторонним движением» информирует о том, что на пересекаемой проезжей части движение осуществляется не только по правой, но и по левой полосе. Следовательно, вы можете поворачивать налево по любой из двух траекторий п 8.6"
        ),
        QuestionDomain(
            9,
            R.mipmap.b14_9,
            "Можно ли Вам развернуться в этом месте?",
            listOf(
                Pair("Можно", true),
                Pair("Можно только в светлое время суток", false),
                Pair("Нельзя", false)
            ),
            "Разворот запрещен в местах с видимостью дороги хотя бы в одном направлении менее 100 м п 8.11. Знак 1.12.1 Опасные повороты с табличкой 8.2.1 Зона действия вне населенного пункта устанавливается за 150-300 м до начала первого поворота. Следовательно, в непосредственной близости от знака развернуться можно, так как табличка показывает не расстояние до опасного участка, а его протяженность."
        ),
        QuestionDomain(
            10,
            R.mipmap.none_image_question,
            "Допускается ли движение автомобилей по тротуарам или пешеходным дорожкам?",
            listOf(
                Pair("Допускается", false),
                Pair(
                    "Допускается только при доставке грузов к торговым и другим предприятиям, расположенным непосредственно у тротуаров или пешеходных дорожек, если отсутствуют другие возможности подъезда",
                    true
                ),
                Pair("Не допускается", false)
            ),
            "Тротуары и пешеходные дорожки не предназначены для движения ТС. Однако как исключение допускается подъезд, если отсутствуют иные возможности, к торговым предприятиям и другим объектам, расположенным непосредственно у этих тротуаров или дорожек при доставке грузов п 9.9."
        ),
        QuestionDomain(
            11,
            R.mipmap.b31_11,
            "В данной ситуации преимущество имеет:",
            listOf(
                Pair("Легковой автомобиль, так как он движется на подъем", true),
                Pair("Грузовой автомобиль, так как он движется на спуск", false),
                Pair(
                    "Грузовой автомобиль, так как препятствие находится на полосе движения легкового автомобиля",
                    false
                )
            ),
            "Знак 1.14 «Крутой подъем» предупреждает водителя легкового автомобиля о приближении к подъему. При затрудненном встречном разъезде на данном участке дороги преимущество имеет водитель легкового автомобиля, поскольку он движется на подъём п 11.7."
        ),
        QuestionDomain(
            12,
            R.mipmap.b31_12,
            "Можно ли Вам поставить автомобиль на стоянку в указанном месте?",
            listOf(
                Pair("Можно", true),
                Pair("Можно только при видимости дороги не менее 100 м.", false),
                Pair("Нельзя", false)
            ),
            "Ставить автомобиль на стоянку в условиях ограниченной видимости (знак 1.11.1 «Опасный поворот») запрещается только на проезжей части п 12.4 и п 12.5 Поставив автомобиль на стоянку на обочине, Вы не нарушите Правил."
        ),
        QuestionDomain(
            13,
            R.mipmap.b31_13,
            "Как Вам следует поступить при повороте налево?",
            listOf(
                Pair(
                    "Остановиться у стоп-линии и дождаться сигнала регулировщика, разрешающего поворот",
                    true
                ),
                Pair(
                    "Выехав на перекресток, остановиться и дождаться сигнала регулировщика, разрешающего поворот",
                    false
                ),
                Pair("Повернуть, уступив дорогу встречному автомобилю", false)
            ),
            "В случае, когда регулировщик расположен к Вам левым или правым боком, а его руки опущены либо вытянуты в стороны, движение Вам разрешено прямо и направо п 6.10, чтобы повернуть налево, Вам необходимо дождаться разрешающего сигнала регулировщика. Ожидать сигнала Вы должны перед стоп-линией п 6.13."
        ),
        QuestionDomain(
            14,
            R.mipmap.b31_14,
            "Вы намерены проехать перекресток в прямом направлении. Ваши действия?",
            listOf(
                Pair(
                    "Проедете перекресток вместе с трамваем, не уступая дорогу грузовому автомобилю",
                    false
                ),
                Pair("Проедете перекресток, уступив дорогу грузовому автомобилю", true),
            ),
            "Руководствуясь правилами проезда нерегулируемых перекрестков равнозначных дорог, Вы должны уступить дорогу приближающемуся справа грузовому автомобилю п 13.11, который, в свою очередь, уступая дорогу трамваю, все же имеет право выехать на пересекаемую проезжую часть."
        ),
        QuestionDomain(
            15,
            R.mipmap.b31_15,
            "Кому Вы обязаны уступить дорогу при движении прямо?",
            listOf(
                Pair(
                    "Только мотоциклу",
                    false
                ),
                Pair("Мотоциклу и легковому автомобилю", false),
                Pair("Автобусу и мотоциклу", false),
                Pair("Всем транспортным средствам", true)
            ),
            "На этом перекрестке неравнозначных дорог (знаки 2.4 «Уступите дорогу» и 8.13  «Направление главной дороги») Вы должны уступить дорогу мотоциклу и автобусу, поскольку они движутся по главной дороге п 13.9. Следует уступить дорогу и подъехавшему справа легковому автомобилю, при разъезде с которым Вы должны руководствоваться правилами проезда перекрестков равнозначных дорог п 13.10 и п 13.11."
        ),
        QuestionDomain(
            16,
            R.mipmap.b10_16,
            "Разрешен ли Вам въезд на железнодорожный переезд в данной ситуации?",
            listOf(
                Pair(
                    "Разрешен",
                    false
                ),
                Pair("Разрешен, если отсутствует приближающийся поезд", false),
                Pair("Запрещен", true)
            ),
            "Независимо от положения шлагбаума Вы должны остановиться перед ним, поскольку красный сигнал светофора запрещает движение через переезд, даже если отсутствует приближающийся поезд п 15.3."
        ),
        QuestionDomain(
            17,
            R.mipmap.none_image_question,
            "При движении в условиях недостаточной видимости можно использовать противотуманные фары:",
            listOf(
                Pair(
                    "Только отдельно от ближнего или дальнего света фар",
                    false
                ),
                Pair("Только совместно с ближним или дальним светом фар", true),
                Pair("Как отдельно, так и совместно с ближним или дальним светом фар", false)
            ),
            "Правила предписывают при движении в условиях недостаточной видимости использовать противотуманные фары только совместно с ближним или дальним светом фар п 19.4."
        ),
        QuestionDomain(
            18,
            R.mipmap.none_image_question,
            "Какие из перечисленных транспортных средств разрешается эксплуатировать без медицинской аптечки?",
            listOf(
                Pair(
                    "Автомобили",
                    false
                ),
                Pair("Автобусы", false),
                Pair("Все мотоциклы", false),
                Pair("Только мотоциклы без бокового прицепа", true)
            ),
            "Из всех перечисленных ТС только мотоциклы без бокового прицепа могут эксплуатироваться без медицинской аптечки. Эксплуатация всех остальных ТС при отсутствии аптечки запрещается п 7.7."
        ),
        QuestionDomain(
            19,
            R.mipmap.none_image_question,
            "При приближении к вершине подъема в темное время суток водителю следует:",
            listOf(
                Pair(
                    "Не переключать дальний свет фар на ближний",
                    false
                ),
                Pair(
                    "Переключать дальний свет фар на ближний только при появлении встречного транспортного средства",
                    false
                ),
                Pair("Всегда переключать дальний свет фар на ближний", false),
                Pair("Только мотоциклы без бокового прицепа", true)
            ),
            "Приближаясь в темное время суток к вершине подъема, рекомендуется всегда переключать дальний свет фар на ближний, чтобы не ослепить водителя ТС, неожиданно появившегося со встречного направления."
        ),
        QuestionDomain(
            20,
            R.mipmap.none_image_question,
            "Какова первая помощь при черепно-мозговой травме, сопровождающейся ранением волосистой части головы?",
            listOf(
                Pair(
                    "Остановить кровотечение прямым давлением на рану и наложить давящую повязку. При потере сознания придать устойчивое боковое положение. По возможности, приложить к голове холод",
                    true
                ),
                Pair(
                    "Фиксировать шейный отдел позвоночника с помощью импровизированной шейной шины(воротника). На рану наложить стерильный ватный тампон, пострадавшего уложить на спину, приподняв ноги. По возможности, к голове приложить холод",
                    false
                ),
                Pair(
                    "Шейную шину не накладывать, рану заклеить медицинским пластырем, пострадавшего уложить на бок",
                    false
                )
            ),
            "анение волосистой части головы сопровождается не обильным, но опасным для жизни кровотечением, которое следует остановить прямым давлением на рану, а затем наложить давящую повязку. Пострадавшему придают устойчивое боковое положение при потере им сознания. (Перечень мероприятий, п. 6.1, 7.6, 8.1, 8.7, 9; Рекомендации, п. 1 «г»; Состав аптечки, п. 1.2 – 1.9). Холод, приложенный к голове, замедляет развитие отека мозга."
        )
// далее еще вопросы
    )
}
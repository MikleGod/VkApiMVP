package com.epam.mikle.vkapimvp.util;

import com.epam.mikle.vkapimvp.models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikle on 19.02.2018.
 */

public class StudentsCreator {
    interface BaseStudents{
        String[] names = {
                "Автушков Александр Николаевич",
                "Ачилов Рустам Закирович",
                "Бараховская Виктория Петровна",
                "Бедрицкая Анастасия Сергеевна",
                "Боринская Виктория Сергеевна",
                "Василевская Алеся Александровна",
                "Веремейчик Алеся Александровна",
                "Гудко Ангелина Валерьевна",
                "Ермолаев Алексей Дмитриевич",
                "Кашковская Екатерина Александровна",
                "Климович Светлана Валентинова",
                "Козел Алина Валерьевна",
                "Комар Николай Александрович",
                "Крокун Светлана Андреевна",
                "Лаворенко Елизавета Викторовна",
                "Маслак Дмитрий Александрович",
                "Пирштук Диана Ивановна",
                "Пичкарь Ангелина Эдуардовна",
                "Побединский Алексей Эдуардович",
                "Потупчик Виталий Андреевич",
                "Рыжкович Диана Игоревна",
                "Сарнова Анастасия Александровна",
                "Селенчик Анастасия Сергеевна",
                "Сушкова Анна Алексеевна",
                "Теулович Арина Сергеевна",
                "Тупека Вероника Николаевна",
                "Хмыско Полина Анатольевна",
                "Шляхова Анастасия Алексеевна"
        };
        String[] vkDomens = {
                "id333524188",
                "id336653500",
                "id164525908",
                "id142932525",
                "id138777721",
                "id153496430",
                "id374995175",
                "angelin_by",
                "id88938776",
                "ekashkovskaya",
                "id.svetkaaaa",
                "all_alien",
                "id202751783",
                "id_kr.svetlana",
                "liz_lavorenko",
                "dmitrymaslak",
                "dana__deli",
                "pingals",
                "id167662757",
                "vvv.patupchyk_by",
                "zhrvlk",
                "nastya_sarnova",
                "id137244882",
                "id244125841",
                "id139398744",
                "klubnika_99",
                "id166802733",
                "id232689543"
        };
    }

    public static List<Student> getBaseStudents(){
        ArrayList<Student> students = new ArrayList<>();
        for (int i=0; i < BaseStudents.names.length; i++){
            students.add(new Student(BaseStudents.names[i], BaseStudents.vkDomens[i]));
        }
        return students;
    }

    private StudentsCreator(){}
}

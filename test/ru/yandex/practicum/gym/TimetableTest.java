package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size() == 1
                && timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size() == 1);

        //Проверить, что за вторник не вернулось занятий
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size() == 1
                && timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size() == 1);
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size() == 2
                && timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).firstKey().getHours() == 13
                && timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).lastKey().getHours() == 20);
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size(), "за понедельник в 13:00 вернулось не одно занятие");
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)).size(), "за понедельник в 14:00 вернулось занятие");
    }

    @Test
    void testGetCountByCoaches() {

        Timetable timetable = new Timetable();

        Group groupChildren = new Group("Акробатика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Измайлов", "Константин", "Викторович");
        Coach coach3 = new Coach("Семёнов", "Владимир", "Сергеевич");

        timetable.addNewTrainingSession(new TrainingSession(groupChildren, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChildren, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChildren, coach3,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChildren, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChildren, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(15, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChildren, coach3,
                DayOfWeek.TUESDAY, new TimeOfDay(20, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChildren, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(16, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coach3,
                DayOfWeek.WEDNESDAY, new TimeOfDay(16, 0)));


        LinkedList<CounterForCoach> countByCoaches = timetable.getCountByCoaches();
        //Проверить, что первый в списке тренер Измайлов
        Assertions.assertEquals("Измайлов", countByCoaches.getFirst().getCoach().getSurname());
        //Проверить, что последний в списке тренер Васильев
        Assertions.assertEquals("Васильев", countByCoaches.getLast().getCoach().getSurname());
        //Проверить, что тренер Семёнов имеет 3 занятия в неделю
        for (CounterForCoach counterForCoach : countByCoaches) {
            if (counterForCoach.getCoach().getSurname().equals("Семёнов")) {
                Assertions.assertEquals(3, counterForCoach.getTrainingSessionCounter());
            }
        }

    }

}

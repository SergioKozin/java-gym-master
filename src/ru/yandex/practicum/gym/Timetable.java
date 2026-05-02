package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, LinkedList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, LinkedList<TrainingSession>> dayMap =
                timetable.computeIfAbsent(trainingSession.getDayOfWeek(), d -> new TreeMap<>());

        // Получаем или создаём список для времени
        LinkedList<TrainingSession> sessions =
                dayMap.computeIfAbsent(trainingSession.getTimeOfDay(), t -> new LinkedList<>());

        // Добавляем тренировку
        sessions.add(trainingSession);
    }

    public TreeMap<TimeOfDay, LinkedList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, LinkedList<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        if (dayMap == null) {
            return new TreeMap<>();
        }
        return dayMap;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, LinkedList<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        if (dayMap == null) {
            return Collections.emptyList();
        }

        List<TrainingSession> sessions = dayMap.get(timeOfDay);
        if (sessions == null) {
            return Collections.emptyList();
        }

        return sessions;
    }

    public LinkedList<CounterForCoach> getCountByCoaches() {
        LinkedList<CounterForCoach> counterOfTrainings = new LinkedList<>();
        boolean found;
        //заполняем
        for (DayOfWeek dayOfWeek : timetable.keySet()) {
            for (LinkedList<TrainingSession> trainingSessions : getTrainingSessionsForDay(dayOfWeek).values()) {
                for (TrainingSession trainingSession : trainingSessions) {
                    found = false;
                    for (CounterForCoach counterForCoach : counterOfTrainings) {
                        if (counterForCoach.getCoach().equals(trainingSession.getCoach())) {
                            counterForCoach.setTrainingSessionCounter(counterForCoach.getTrainingSessionCounter() + 1);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        counterOfTrainings.add(new CounterForCoach(trainingSession.getCoach(), 1));
                    }
                }
            }
        }

        // сортируем объекты CounterForCoach
        Collections.sort(counterOfTrainings.reversed());
        return counterOfTrainings;
    }
}

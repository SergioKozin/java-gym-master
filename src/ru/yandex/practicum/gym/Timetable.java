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

    public NavigableMap<TimeOfDay, LinkedList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, LinkedList<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        if (dayMap == null) {
            return Collections.emptyNavigableMap();
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

    public LinkedHashMap<Coach, Integer> getCountByCoaches() {
        Map<Coach, Integer> counterOfTrainings = new HashMap<>();
        //заполняем
        for (DayOfWeek dayOfWeek : timetable.keySet()) {
            for (LinkedList<TrainingSession> trainingSessions : getTrainingSessionsForDay(dayOfWeek).values()) {
                for (TrainingSession trainingSession : trainingSessions) {
                    if (counterOfTrainings.containsKey(trainingSession.getCoach())) {
                        counterOfTrainings.put(trainingSession.getCoach(),
                                counterOfTrainings.get(trainingSession.getCoach()) + 1);
                    } else {
                        counterOfTrainings.put(trainingSession.getCoach(), 1);
                    }
                }
            }
        }

        // превращаем в список
        List<Map.Entry<Coach, Integer>> list = new ArrayList<>(counterOfTrainings.entrySet());

        // сортируем по значению
        list.sort(Map.Entry.<Coach, Integer>comparingByValue().reversed());
        //создаем и заполняем мапу отсортированную по значению
        LinkedHashMap<Coach, Integer> sortedCounterOfTrainings = new LinkedHashMap<>();

        for (Map.Entry<Coach, Integer> entry : list) {
            sortedCounterOfTrainings.put(entry.getKey(), entry.getValue());
        }

        return sortedCounterOfTrainings;
    }
}

package ru.yandex.practicum.gym;

public class CounterForCoach implements Comparable<CounterForCoach> {
    private Coach coach;
    private Integer trainingSessionCounter;

    public CounterForCoach(Coach coach, Integer trainingSessionCounter) {
        this.coach = coach;
        this.trainingSessionCounter = trainingSessionCounter;
    }

    @Override
    public int compareTo(CounterForCoach o) {
        return this.trainingSessionCounter - o.trainingSessionCounter;
    }

    public Coach getCoach() {
        return coach;
    }


    public Integer getTrainingSessionCounter() {
        return trainingSessionCounter;
    }

    public void setTrainingSessionCounter(Integer trainingSessionCounter) {
        this.trainingSessionCounter = trainingSessionCounter;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CounterForCoach counterForCoach = (CounterForCoach) obj;
        return coach.equals(counterForCoach.coach); // Сравниваем только по имени
    }

    @Override
    public int hashCode() {
        return coach.hashCode();
    }

}

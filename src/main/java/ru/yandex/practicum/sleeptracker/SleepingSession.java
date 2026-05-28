package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime start;
    private final LocalDateTime finish;
    private final Duration sleepDuration;
    private final SleepQuality sleepQuality;
    private UserType userType;

    public SleepingSession(LocalDateTime start, LocalDateTime finish, SleepQuality sleepQuality) {
        this.start = start;
        this.finish = finish;
        this.sleepDuration = Duration.between(start, finish);
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public int getSleepDurationMinutes() {
        return Math.toIntExact(sleepDuration.toMinutes());
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }
}

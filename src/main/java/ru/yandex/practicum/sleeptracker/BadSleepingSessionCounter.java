package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BadSleepingSessionCounter
        implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private final String resultDescription = "Количество сессий с плохим качеством сна: ";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        List<SleepingSession> badSleepingSessions = sleepingSessions.stream()
                .filter(session -> session.getSleepQuality() == SleepQuality.BAD)
                .collect(Collectors.toList());
        return new SleepAnalysisResult(resultDescription + badSleepingSessions.size());
    }
}

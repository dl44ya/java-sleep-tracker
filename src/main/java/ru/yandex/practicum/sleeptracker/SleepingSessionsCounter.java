package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class SleepingSessionsCounter
        implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private final String resultDescription = "Всего сессий сна записано: ";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        int result = sleepingSessions.size();
        return new SleepAnalysisResult(resultDescription + result);
    }
}

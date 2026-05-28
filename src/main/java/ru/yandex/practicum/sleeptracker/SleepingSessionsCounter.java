package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class SleepingSessionsCounter
        implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        int result = sleepingSessions.size();
        return new SleepAnalysisResult("Всего сессий сна записано: " + result);
    }
}

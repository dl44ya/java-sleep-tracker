package ru.yandex.practicum.sleeptracker;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MaxSessionDurationAnalyzer
        implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private final String resultDescription = "Максимальная продолжительность сна в минутах: ";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        Optional<SleepingSession> result = sleepingSessions.stream()
                .max(Comparator.comparing(SleepingSession::getSleepDurationMinutes));
        return new SleepAnalysisResult(resultDescription + result.get().getSleepDurationMinutes());
    }
}

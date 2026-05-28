package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.Locale;
import java.util.OptionalDouble;
import java.util.function.Function;

public class AverageSessionDurationAnalyzer
        implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        OptionalDouble result = sleepingSessions.stream()
                .mapToInt(SleepingSession::getSleepDurationMinutes)
                .average();
        return new SleepAnalysisResult(String.format(Locale.US, "Средняя продолжительность сна: %.2f минут", result.getAsDouble()));
    }
}

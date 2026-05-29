package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.Locale;
import java.util.OptionalDouble;
import java.util.function.Function;

public class AverageSessionDurationAnalyzer
        implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private final String format = "%.2f";
    private final String resultDescription = "Средняя продолжительность сна в минутах: " + format;

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        OptionalDouble result = sleepingSessions.stream()
                .mapToInt(SleepingSession::getSleepDurationMinutes)
                .average();
        return new SleepAnalysisResult(String.format(Locale.US, resultDescription, result.getAsDouble()));
    }
}

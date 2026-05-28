package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleeplessNightsCounter
        implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        long nightsTotal = ChronoUnit.DAYS.between(sleepingSessions.getFirst().getStart().toLocalDate(),
                sleepingSessions.getLast().getStart().toLocalDate()) + 1;

        List<SleepingSession> sleepNights = sleepingSessions.stream()
                .filter(session -> {
                    if (session.getStart()
                            .isBefore(LocalDateTime.of(session.getStart().toLocalDate(), LocalTime.of(6, 0)))) {
                        return true;
                    } else if (session.getStart().toLocalDate()
                            .isBefore(session.getFinish().toLocalDate())) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());

        return new SleepAnalysisResult("Бессонных ночей: " + (nightsTotal - sleepNights.size()));
    }
}

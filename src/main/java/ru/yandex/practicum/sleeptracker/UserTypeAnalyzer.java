package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserTypeAnalyzer
        implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        Map<UserType, Integer> userTypeMap = sleepingSessions.stream()
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
                .map(session -> {

                    LocalTime start = session.getStart().toLocalTime();
                    LocalTime finish = session.getFinish().toLocalTime();

                    if ((start.isAfter(LocalTime.of(23, 0))
                            || start.isAfter(LocalTime.of(0, 0))
                            || start.equals(LocalTime.of(0, 0)))
                            && (finish.isAfter(LocalTime.of(9, 0))
                            || finish.equals(LocalTime.of(9, 0)))) {
                        session.setUserType(UserType.OWL);
                    } else if (start.isBefore(LocalTime.of(22, 0))
                            && finish.isBefore(LocalTime.of(7, 0))) {
                        session.setUserType(UserType.EARLY_BIRD);
                    } else {
                        session.setUserType(UserType.PIGEON);
                    }
                    return session.getUserType();

                })
                .collect(Collectors.toMap(type -> type, type -> 1, Integer::sum));

        Optional<Integer> maxNightsCountByUserType = userTypeMap.values().stream()
                .max(Integer::compareTo);

        List<UserType> frequentTypes = userTypeMap.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), maxNightsCountByUserType.get()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (frequentTypes.size() > 1) {
            return new SleepAnalysisResult("Вы " + UserType.PIGEON);
        }

        return new SleepAnalysisResult("Вы " + frequentTypes.getFirst());
    }
}

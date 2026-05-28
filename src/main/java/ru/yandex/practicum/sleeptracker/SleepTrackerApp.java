package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepTrackerApp {
    private static final List<Function<List<SleepingSession>, SleepAnalysisResult>> analysisFunctions = Arrays.asList(
            new SleepingSessionsCounter(),
            new MinSessionDurationAnalyzer(),
            new MaxSessionDurationAnalyzer(),
            new AverageSessionDurationAnalyzer(),
            new BadSleepingSessionCounter(),
            new SleeplessNightsCounter(),
            new UserTypeAnalyzer()
    );

    public static void main(String[] args) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

            List<String> lines = Files.readAllLines(Paths.get(args[0]));
            List<SleepingSession> sleepingSessions = lines.stream()
                    .map(line -> line.split(";"))
                    .map(data -> new SleepingSession(
                            LocalDateTime.parse(data[0], formatter),
                            LocalDateTime.parse(data[1], formatter),
                            SleepQuality.valueOf(data[2])))
                    .collect(Collectors.toList());

            analysisFunctions.stream()
                    .map(analysisFunction -> analysisFunction.apply(sleepingSessions))
                    .forEach(result -> System.out.println(result.getDescription()));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
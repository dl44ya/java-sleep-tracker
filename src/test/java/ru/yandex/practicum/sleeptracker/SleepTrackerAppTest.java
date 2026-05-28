package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTrackerAppTest {

    private List<SleepingSession> sleepingSessions;

    @BeforeEach
    void data() {
        LocalDateTime start1 = LocalDateTime.of(2026, 1, 1, 22, 0);
        LocalDateTime start2 = LocalDateTime.of(2026, 1, 2, 22, 0);
        LocalDateTime start3 = LocalDateTime.of(2026, 1, 3, 22, 0);
        LocalDateTime start4 = LocalDateTime.of(2026, 1, 4, 22, 0);
        LocalDateTime start5 = LocalDateTime.of(2026, 1, 8, 22, 0);
        sleepingSessions = Arrays.asList(
                new SleepingSession(start1, start1.plusHours(8), SleepQuality.GOOD),
                new SleepingSession(start2, start2.plusHours(9), SleepQuality.NORMAL),
                new SleepingSession(start3, start3.plusHours(6), SleepQuality.BAD),
                new SleepingSession(start4, start4.plusHours(1), SleepQuality.NORMAL),
                new SleepingSession(start5, start5.plusHours(7), SleepQuality.GOOD)
        );
    }

    @Test
    void shouldReturn3SleepingSessionsCounter() {
        SleepingSessionsCounter counter = new SleepingSessionsCounter();
        SleepAnalysisResult result = counter.apply(sleepingSessions);

        assertEquals("Всего сессий сна записано: 5", result.getDescription());
    }

    @Test
    void shouldReturnMinSleepingSessionDifferentDuration() {
        MinSessionDurationAnalyzer analyzer = new MinSessionDurationAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sleepingSessions);

        assertEquals("Минимальная продолжительность сна: 60 минут", result.getDescription());
    }

    @Test
    void shouldReturnMinSleepingSessionSameDuration() {
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 22, 0);
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(start, start.plusHours(7), SleepQuality.BAD),
                new SleepingSession(start, start.plusHours(7), SleepQuality.NORMAL)
        );
        MinSessionDurationAnalyzer analyzer = new MinSessionDurationAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals("Минимальная продолжительность сна: 420 минут", result.getDescription());
    }

    @Test
    void shouldReturnMaxSleepingSessionDifferentDuration() {
        MaxSessionDurationAnalyzer analyzer = new MaxSessionDurationAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sleepingSessions);

        assertEquals("Максимальная продолжительность сна: 540 минут", result.getDescription());
    }

    @Test
    void shouldReturnMaxSleepingSessionSameDuration() {
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 22, 0);
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(start, start.plusHours(7), SleepQuality.BAD),
                new SleepingSession(start, start.plusHours(7), SleepQuality.NORMAL)
        );
        MaxSessionDurationAnalyzer analyzer = new MaxSessionDurationAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals("Максимальная продолжительность сна: 420 минут", result.getDescription());
    }

    @Test
    void shouldReturnAverageSleepingSessionDurationMultipleSessions() {
        AverageSessionDurationAnalyzer analyzer = new AverageSessionDurationAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sleepingSessions);

        assertEquals("Средняя продолжительность сна: 372,00 минут", result.getDescription());
    }

    @Test
    void shouldReturnAverageSleepingSessionDurationSingleSession() {
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 22, 0);
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(start, start.plusHours(7), SleepQuality.BAD)
        );
        AverageSessionDurationAnalyzer analyzer = new AverageSessionDurationAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals("Средняя продолжительность сна: 420,00 минут", result.getDescription());
    }

    @Test
    void shouldReturn1BadQualitySleepingSession() {
        BadSleepingSessionCounter counter = new BadSleepingSessionCounter();
        SleepAnalysisResult result = counter.apply(sleepingSessions);

        assertEquals("Количество сессий с плохим качеством сна: 1", result.getDescription());
    }

    @Test
    void shouldReturn0BadQualitySleepingSession() {
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 22, 0);
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(start, start.plusHours(7), SleepQuality.NORMAL),
                new SleepingSession(start, start.plusHours(7), SleepQuality.GOOD)
        );
        BadSleepingSessionCounter counter = new BadSleepingSessionCounter();
        SleepAnalysisResult result = counter.apply(sessions);

        assertEquals("Количество сессий с плохим качеством сна: 0", result.getDescription());
    }

    @Test
    void shouldReturn5SleeplessNights() {
        SleeplessNightsCounter counter = new SleeplessNightsCounter();
        SleepAnalysisResult result = counter.apply(sleepingSessions);

        assertEquals("Бессонных ночей: 4", result.getDescription());
    }

    @Test
    void shouldReturnAllNightsSleepless() {
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 22, 0);
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(start, start.plusHours(1), SleepQuality.NORMAL),
                new SleepingSession(start.plusDays(1), start.plusDays(1).plusHours(1), SleepQuality.GOOD)
        );
        SleeplessNightsCounter counter = new SleeplessNightsCounter();
        SleepAnalysisResult result = counter.apply(sessions);

        assertEquals("Бессонных ночей: 2", result.getDescription());
    }

    @Test
    void shouldReturn0SleeplessNights() {
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 22, 0);
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(start, start.plusHours(8), SleepQuality.NORMAL),
                new SleepingSession(start.plusDays(1), start.plusDays(1).plusHours(8), SleepQuality.NORMAL),
                new SleepingSession(start.plusDays(2), start.plusDays(2).plusHours(8), SleepQuality.NORMAL)
        );
        SleeplessNightsCounter counter = new SleeplessNightsCounter();
        SleepAnalysisResult result = counter.apply(sessions);

        assertEquals("Бессонных ночей: 0", result.getDescription());
    }

    @Test
    void shouldReturn0SleeplessNightsLongSession() {
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 22, 0);
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(start, start.plusDays(2).plusHours(5), SleepQuality.NORMAL)
        );
        SleeplessNightsCounter counter = new SleeplessNightsCounter();
        SleepAnalysisResult result = counter.apply(sessions);

        assertEquals("Бессонных ночей: 0", result.getDescription());
    }

    @Test
    void shouldReturnPigeonUserType() {
        UserTypeAnalyzer analyzer = new UserTypeAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sleepingSessions);

        assertEquals("Вы PIGEON", result.getDescription());
    }

    @Test
    void shouldReturnOwlUserType() {
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 0, 0);
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(start, start.plusHours(10), SleepQuality.NORMAL)
        );
        UserTypeAnalyzer analyzer = new UserTypeAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals("Вы OWL", result.getDescription());
    }

    @Test
    void shouldReturnEarlyBirdUserType() {
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 21, 0);
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(start, start.plusHours(6), SleepQuality.NORMAL)
        );
        UserTypeAnalyzer analyzer = new UserTypeAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals("Вы EARLY_BIRD", result.getDescription());
    }
}
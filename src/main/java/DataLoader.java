import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DataLoader {

    @SneakyThrows
    public static void main(String[] args) {
        Yaml attendance = new Yaml();
        List<Map<String, Object>> result = attendance.load(new FileReader("data/training_games.yaml"));

        Yaml activePlayer = new Yaml();
        Map<String, List<String>> activePlayers = activePlayer.load(new FileReader("data/activePlayer.yaml"));


        Map<String, Integer> playerTrainings = new HashMap<>();

        long trainingCount = result.stream()
                .filter(training -> training.get("type").equals("training"))
                .count();

        activePlayers.get("activePlayer").forEach(player -> {

            AtomicInteger trainingsCount = new AtomicInteger();


            result.stream()
                    .filter(training -> training.get("type").equals("training"))
                    .forEach(game -> {
                        if (((List<String>) game.get("players_present")).contains(player)) {
                            trainingsCount.getAndIncrement();
                        }
                    });

            playerTrainings.put(player, trainingsCount.get());

        });


        printMapSortedByValue(playerTrainings);

        AttendanceChart attendanceChart = new AttendanceChart();
        attendanceChart.setTrainingCount(trainingCount);
        attendanceChart.generateAttendanceChart(playerTrainings);


    }

    public static void printMapSortedByValue(Map<String, Integer> map) {
        map.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(System.out::println);
    }


}
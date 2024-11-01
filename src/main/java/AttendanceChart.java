import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceChart {

    public void generateAttendanceChart(Map<String, Integer> attendanceMap) throws IOException {
        // Sort the attendance map by attendance count in descending order
        Map<String, Integer> sortedAttendanceMap = attendanceMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        java.util.LinkedHashMap::new
                ));

        // Create the dataset for the chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Populate the dataset with the sorted attendance data
        for (Map.Entry<String, Integer> entry : sortedAttendanceMap.entrySet()) {
            dataset.addValue(entry.getValue(), "Attendance", entry.getKey());
        }

        // Create the bar chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "Training Attendance since 2024-10-23",
                "Player",
                "Attendance Count",
                dataset
        );

        // Customize the y-axis to display integers only
        CategoryPlot plot = barChart.getCategoryPlot();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // Save the chart as a JPEG image
        File chartFile = new File("graphics/player_attendance_chart.jpeg");
        ChartUtils.saveChartAsJPEG(chartFile, barChart, 1800, 600);

        System.out.println("Attendance chart saved as player_attendance_chart.jpeg");
    }
}

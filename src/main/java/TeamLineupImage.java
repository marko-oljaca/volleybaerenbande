import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TeamLineupImage {


    public static void main(String[] args) {
        TeamLineupImage teamLineupImage = new TeamLineupImage();
        List<String> players = List.of(
                "Mia", "Gerold", "Fabian",
                "Laura", "Dominik", "Nathan");
        List<String> benchPlayers = List.of("Florian", "Tomas");
        String referee = "Tomas";
        String filePath = "graphics/team_lineup_2024_11_04.png";

        try {            teamLineupImage.createLineupImage(players, benchPlayers, referee, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createLineupImage(List<String> players, List<String> benchPlayers, String referee, String filePath) throws IOException {
        // Image size
        int width = 800;
        int height = 700;

        // Create a buffered image with a white background
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Set up font and color for drawing text
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.setColor(Color.BLACK);

        // Draw court boundaries
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(100, 100, 600, 400); // Main court rectangle

        // Define player positions on the court (basic 6-player volleyball formation)
        int[][] positions = {
                {200, 150}, // Player 1 (Front Left)
                {400, 150}, // Player 2 (Front Center)
                {600, 150}, // Player 3 (Front Right)
                {200, 400}, // Player 4 (Back Left)
                {400, 400}, // Player 5 (Back Center)
                {600, 400}  // Player 6 (Back Right)
        };

        // Draw each player's name in their respective position
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < players.size() && i < positions.length; i++) {
            int x = positions[i][0];
            int y = positions[i][1];
            g2d.drawString(players.get(i), x - 30, y);
        }

        // Draw referee below the court
        if (referee != null && !referee.isEmpty()) {
            g2d.drawString("Referee: " + referee, 350, 550);
        }

        // Draw bench players below the court
        if (benchPlayers != null && !benchPlayers.isEmpty()) {
            g2d.drawString("Bench:", 50, 600);
            int benchX = 150;
            int benchY = 600;

            for (String benchPlayer : benchPlayers) {
                g2d.drawString(benchPlayer, benchX, benchY);
                benchX += 100; // Space out bench players horizontally
            }
        }

        // Clean up and save the image
        g2d.dispose();
        ImageIO.write(image, "png", new File(filePath));
        System.out.println("Team lineup image saved as " + filePath);
    }
}

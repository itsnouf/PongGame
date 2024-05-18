import java.awt.*;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PongGame {
    public static void main(String[] args) throws IOException {
        @SuppressWarnings("unused")
        LoginSignupProxy loginSignupProxy = new LoginSignupProxy();
        Adapter adapter = new Adapter(loginSignupProxy);
        adapter.ShowGUI();

        // Ask for ball speed choice
        int speedChoice = askForBallSpeed();

        // Create the game panel with default paddle colors
        GamePanel gamePanel = GamePanel.getInstance(Color.GREEN, Color.YELLOW);

        // Set the ball speed based on the choice
        gamePanel.newBall(speedChoice); // Call newBall with the chosen speed

        // Create the game frame and add the game panel
        GameFrame frame = new GameFrame();
        frame.add(gamePanel);
        frame.pack();
        frame.setVisible(true);
    }

    private static int askForBallSpeed() {
        System.out.println("Enter the ball speed (1 for slow, 2 for medium, 3 for fast):");
        int speedChoice = 2; // Default to medium speed
        try (Scanner scanner = new Scanner(System.in)) {
            if (scanner.hasNextInt()) {
                speedChoice = scanner.nextInt();
            }
        } catch (NoSuchElementException e) {
            System.out.println("Error reading input. Using medium speed as default.");
        }
        return speedChoice;
    }
}



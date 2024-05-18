import java.awt.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.IOException;

interface GameState {
    void doAction(PongGame game) throws IOException;
}



class InitState implements GameState {
    @Override
    public void doAction(PongGame game) throws IOException {
        System.out.println("Game is in initialization state");
        @SuppressWarnings("unused")
        LoginSignupProxy loginSignupProxy = new LoginSignupProxy();
        Adapter adapter = new Adapter(loginSignupProxy);
        adapter.ShowGUI();
        game.setState(this);
    }

    @Override
    public String toString() {
        return "Init State";
    }
}

class SetupState implements GameState {
    @Override
    public void doAction(PongGame game) {
        System.out.println("Game is in setup state");

        // Ask for ball speed choice
        int speedChoice = PongGame.askForBallSpeed();

        // Create the game panel with default paddle colors
        GamePanel gamePanel = GamePanel.getInstance(Color.GREEN, Color.YELLOW);

        // Set the ball speed based on the choice
        gamePanel.newBall(speedChoice); // Call newBall with the chosen speed

        // Create the game frame and add the game panel
        GameFrame frame = new GameFrame();
        frame.add(gamePanel);
        frame.pack();
        frame.setVisible(true);

        game.setState(this);
    }

    @Override
    public String toString() {
        return "Setup State";
    }
}

class PlayState implements GameState {
    @Override
    public void doAction(PongGame game) {
        System.out.println("Game is in play state");
        // Implementation for playing state can be added here
        game.setState(this);
    }

    @Override
    public String toString() {
        return "Play State";
    }
}

public class PongGame {
    private GameState state;

    public PongGame() {
        state = null;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public GameState getState() {
        return state;
    }

    public static void main(String[] args) throws IOException {
        PongGame game = new PongGame();

        // Initial state
        InitState initState = new InitState();
        initState.doAction(game);
        System.out.println(game.getState().toString());

        // Setup state
        SetupState setupState = new SetupState();
        setupState.doAction(game);
        System.out.println(game.getState().toString());

        // Transition to play state (for demonstration)
        PlayState playState = new PlayState();
        playState.doAction(game);
        System.out.println(game.getState().toString());
    }

    public static int askForBallSpeed() {
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

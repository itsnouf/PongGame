import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, ScoreObserver {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;

    private static GamePanel instance = null;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    private Score score;

    private Color paddle1Color; // Color of paddle 1
    private Color paddle2Color; // Color of paddle 2

    // Private constructor
    private GamePanel() {
        newPaddles();
        score = Score.getInstance(GAME_WIDTH, GAME_HEIGHT);
        score.addObserver(this);
        this.setFocusable(true);
        this.addKeyListener(new KeyController()); // Add KeyController as the key listener
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();

        paddle1Color = Color.white; // Default color for paddle 1
        paddle2Color = Color.white; // Default color for paddle 2
    }

    // Factory method with color selection
    public static GamePanel getInstance(Color paddle1Color, Color paddle2Color) {
        if (instance == null) {
            instance = new GamePanel();
            instance.setPaddleColors(paddle1Color, paddle2Color);
        }
        return instance;
    }

    // Method to set the paddle colors
    public void setPaddleColors(Color paddle1Color, Color paddle2Color) {
        this.paddle1Color = paddle1Color;
        this.paddle2Color = paddle2Color;
    }
    
    public void newBall(int speedChoice) {
        random = new Random();
        int ballX = (GAME_WIDTH / 2) - (BALL_DIAMETER / 2);
        int ballY = random.nextInt(GAME_HEIGHT - BALL_DIAMETER);
    
        Ball ball;
        if (speedChoice == 1) {
            ball = new SlowBall(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);
        } else if (speedChoice == 2) {
            ball = new MediumBall(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);
        } else if (speedChoice == 3) {
            ball = new FastBall(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);
        } else {
            System.out.println("Invalid speed choice. Using medium speed as default.");
            ball = new MediumBall(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);
        }
    
        this.ball = ball;
    }

    public void newPaddles() {
        paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH,
                PADDLE_HEIGHT, 2);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        paddle1.setColor(paddle1Color);
        paddle2.setColor(paddle2Color);

        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        Toolkit.getDefaultToolkit().sync();
    }

    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision() {
        // Bounce ball off top & bottom window edges
        if (ball.y <= 0) {
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }
        // Bounce ball off paddles
        if (ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; // Optional for more difficulty
            if (ball.yVelocity > 0)
                ball.yVelocity++; // Optional for more difficulty
            else
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if (ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; // Optional for more difficulty
            if (ball.yVelocity > 0)
                ball.yVelocity++; // Optional for more difficulty
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        // Stops paddles at window edges
        if (paddle1.y <= 0)
            paddle1.y = 0;
        if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        if (paddle2.y <= 0)
            paddle2.y = 0;
        if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
        // Give a player 1 point and create new paddles & ball
        if (ball.x <= 0) {
            score.incrementPlayer2();
            newPaddles();
            newBall(2); // Adjust the default speed choice here (e.g., medium)
            System.out.println("Player 2: " + score.getPlayer2());
        }
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            score.incrementPlayer1();
            newPaddles();
            newBall(2); // Adjust the default speed choice here (e.g., medium)
            System.out.println("Player 1: " + score.getPlayer1());
        }
    }

    public void run() {
        // Game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int maxRounds = 5; // Set the maximum number of rounds
        int roundsPlayed = 0; // Track the number of rounds played

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;

                // Check if a player has won the current round
                if (score.getPlayer1() >= maxRounds || score.getPlayer2() >= maxRounds) {
                    roundsPlayed++;
                    if (roundsPlayed >= maxRounds) {
                        // The game is over after completing the maximum rounds
                        // You can add cleanup or display a message indicating the end of the game.
                        System.out.println("Game Over!");
                        break;
                    } else {
                        // Start a new round
                        newPaddles();
                        newBall(2); // Adjust the default speed choice here (e.g., medium)
                        System.out.println("Starting Round " + (roundsPlayed + 1));
                    }
                }
            }
        }
    }

    public class KeyController extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }

    public void updateScore(int player1Score, int player2Score) {
        System.out.println("Player 1 Score: " + player1Score);
        System.out.println("Player 2 Score: " + player2Score);
    }
}



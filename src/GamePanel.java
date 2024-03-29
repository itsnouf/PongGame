import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

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
    boolean p1cpu = false;
    boolean p2cpu = false;
    CPU cpu = new CPU();
    private Color paddle1Color; // Color of paddle 1
    private Color paddle2Color; // Color of paddle 2

    // Private constructor
    private GamePanel() {
        newPaddles();
        newBall();
        score = Score.getInstance(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();

        paddle1Color = Color.white; // Default color for paddle 1
        paddle2Color = Color.white; // Default color for paddle 2
      
    }

    // Factory method with color selection
    public static GamePanel getInstance(Color paddle1Color, Color paddle2Color) {
        GamePanel instance = new GamePanel();
        instance.setPaddleColors(paddle1Color, paddle2Color);
        return instance;
    }

    // Method to set the paddle colors
    public void setPaddleColors(Color paddle1Color, Color paddle2Color) {
        this.paddle1Color = paddle1Color;
        this.paddle2Color = paddle2Color;
    }

    public void newBall() {
        random = new Random();
        ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER),
                BALL_DIAMETER, BALL_DIAMETER);
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
        cpu.draw(g, p1cpu, p2cpu);
        Toolkit.getDefaultToolkit().sync();
    }

    public void move() {
        if (p1cpu)
            cpu.control(paddle1, ball);
        if (p2cpu)
            cpu.control(paddle2, ball);
        paddle1.move();
        paddle2.move();
        ball.move();
    }
    public void checkCollision() {

        //bounce ball off top & bottom window edges
        if(ball.y <=0) {
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }
        //bounce ball off paddles
        if(ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; //optional for more difficulty
            if(ball.yVelocity>0)
                ball.yVelocity++; //optional for more difficulty
            else
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if(ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; //optional for more difficulty
            if(ball.yVelocity>0)
                ball.yVelocity++; //optional for more difficulty
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        //stops paddles at window edges
        if(paddle1.y<=0)
            paddle1.y=0;
        if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
            paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
        if(paddle2.y<=0)
            paddle2.y=0;
        if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
            paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;
        //give a player 1 point and creates new paddles & ball
        if(ball.x <=0) {
            score.incrementPlayer2();
            newPaddles();
            newBall();
            System.out.println("Player 2: "+score.getPlayer2());
        }
        if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
            score.incrementPlayer1();
            newPaddles();
            newBall();
            System.out.println("Player 1: "+score.getPlayer1());
        }
    }
    public void run() {
        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int maxRounds = 5; // Set the maximum number of rounds
    
        while (score.getPlayer1() < maxRounds && score.getPlayer2() < maxRounds) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
    
                // Additional check to stop the game after reaching the maximum rounds
                if (score.getPlayer1() >= maxRounds ||score.getPlayer2() >= maxRounds) {
                    // You can add cleanup or display a message indicating the end of the game.
                    System.out.println("Game Over!");
                    break; // Exit the loop and stop the game
                }
            }
        }
    }
    
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e) {
            if (!p1cpu) paddle1.keyPressed(e);
            if (!p2cpu) paddle2.keyPressed(e);
            if (e.getKeyChar() == 'o') p1cpu = !p1cpu;
            if (e.getKeyChar() == 'p') p2cpu = !p2cpu;
        }
        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}


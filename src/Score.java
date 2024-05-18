
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
interface ScoreObserver {
    void updateScore(int player1Score, int player2Score);
}

public class Score extends Rectangle {

    private static Score instance = null;

    private static int GAME_WIDTH;
    private static int GAME_HEIGHT;
    private int player1;
    private int player2;
    private List<ScoreObserver> observers;

    private Score(int GAME_WIDTH, int GAME_HEIGHT) {
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
        this.observers = new ArrayList<>();
    }

    public static Score getInstance(int GAME_WIDTH, int GAME_HEIGHT) {
        if (instance == null) {
            instance = new Score(GAME_WIDTH, GAME_HEIGHT);
        }
        return instance;
    }

    public void addObserver(ScoreObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ScoreObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (ScoreObserver observer : observers) {
            observer.updateScore(player1, player2);
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Consolas",Font.PLAIN,60));

        g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);

        g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10), (GAME_WIDTH/2)-85, 50);
        g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10), (GAME_WIDTH/2)+20, 50);
    }

    public int getPlayer1() {
        return player1;
    }

    public int getPlayer2() {
        return player2;
    }

    public void incrementPlayer1() {
        player1++;
        notifyObservers(); // Notify observers when player 1's score changes
    }

    public void incrementPlayer2() {
        player2++;
        notifyObservers(); // Notify observers when player 2's score changes
    }
}



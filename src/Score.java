import java.awt.*;

public class Score extends Rectangle{

    private static Score instance = null;
    
    private static int GAME_WIDTH;
    private static int GAME_HEIGHT;
    private int player1;
    private int player2;

    private Score(int GAME_WIDTH, int GAME_HEIGHT) {
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
    }
    public static Score getInstance(int GAME_WIDTH, int GAME_HEIGHT) {
        if (instance == null) {
            instance = new Score(GAME_WIDTH, GAME_HEIGHT);
        }
        return instance;
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
    }

    public void incrementPlayer2() {
        player2++;
    }
}

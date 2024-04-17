import java.awt.Color;
import javax.swing.JFrame;

public class GameFrame extends JFrame {

     private static final long serialVersionUID = 1L;

    public GameFrame() {
        GamePanel panel = GamePanel.getInstance(Color.pink, Color.white);
        panel.setFocusable(true); // Allow panel to get focus
        panel.requestFocusInWindow(); // Set focus to the GamePanel

        this.add(panel);
        this.setTitle("Pong by Borrozzino, Macrev & Igor");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}

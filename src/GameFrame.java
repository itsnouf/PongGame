import javapong.main.GamePanel;
import java.awt.*;
import javax.swing.*;
public class GameFrame extends JFrame{

   

    GameFrame(){
        // creat an object by calling the static method 
       GamePanel panel = GamePanel.getInstance(); 
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

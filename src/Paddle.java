import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle{

    int id;
    int yVelocity;
    int speed = 10;
    private Color color; // Color of the paddle

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id){
        super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
        this.id=id;
        this.color = (id == 1) ? Color.green : Color.yellow; // Default color for the paddle
    }
    
    public void setColor(Color color) {
        this.color = color;
    }

    public void keyPressed(KeyEvent e) {
        switch(id) {
            case 1:
                if(e.getKeyCode()==KeyEvent.VK_W) yVelocity = -speed;
                if(e.getKeyCode()==KeyEvent.VK_S) yVelocity = speed;
                break;
            case 2:
                if(e.getKeyCode()==KeyEvent.VK_UP) yVelocity = -speed;
                if(e.getKeyCode()==KeyEvent.VK_DOWN) yVelocity = speed;
                break;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        switch(id) {
            case 1:
                if(e.getKeyCode()==KeyEvent.VK_W || e.getKeyCode()==KeyEvent.VK_S) yVelocity = 0;
                break;
            case 2:
                if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_DOWN) yVelocity = 0;
                break;
        }
    }
    
    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }
    public void move() {
        y = y + yVelocity;
    }
    
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}

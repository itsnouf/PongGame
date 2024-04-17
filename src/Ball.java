import java.awt.*;
import java.util.*;

public abstract class Ball extends Rectangle {

    Random random;
    int xVelocity;
    int yVelocity;

    Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        random = new Random();
        setSpeed();
    }
    
    

    public abstract void setSpeed();

    public void setXDirection(int randomXDirection) {
        xVelocity = randomXDirection;
    }

    public void setYDirection(int randomYDirection) {
        yVelocity = randomYDirection;
    }

    public int getXDirection() {
        return xVelocity;
    }

    public int getYDirection() {
        return yVelocity;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(x, y, height, width);
    }
}

class FastBall extends Ball {
    private static final int FAST_SPEED = 8;

    FastBall(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void setSpeed() {
        int randomXDirection = random.nextInt(2);
        if (randomXDirection == 0)
            randomXDirection--;
        setXDirection(randomXDirection * FAST_SPEED);

        int randomYDirection = random.nextInt(2);
        if (randomYDirection == 0)
            randomYDirection--;
        setYDirection(randomYDirection * FAST_SPEED);
    }
}

class MediumBall extends Ball {
    private static final int MEDIUM_SPEED = 5;

    MediumBall(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void setSpeed() {
        int randomXDirection = random.nextInt(2);
        if (randomXDirection == 0)
            randomXDirection--;
        setXDirection(randomXDirection * MEDIUM_SPEED);

        int randomYDirection = random.nextInt(2);
        if (randomYDirection == 0)
            randomYDirection--;
        setYDirection(randomYDirection * MEDIUM_SPEED);
    }
}

class SlowBall extends Ball {
    private static final int SLOW_SPEED = 3;

    SlowBall(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void setSpeed() {
        int randomXDirection = random.nextInt(2);
        if (randomXDirection == 0)
            randomXDirection--;
        setXDirection(randomXDirection * SLOW_SPEED);

        int randomYDirection = random.nextInt(2);
        if (randomYDirection == 0)
            randomYDirection--;
        setYDirection(randomYDirection * SLOW_SPEED);
    }
}

import java.util.Scanner;

public class PongGame {

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        GameFrame frame = new GameFrame();
        //Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);
System.out.println("Enter the ball speed (1 for slow, 2 for medium, 3 for fast):");
int speedChoice = scanner.nextInt();
scanner.close(); // Close the Scanner object



        System.out.println("Select the ball speed:");
        System.out.println("1. Fast");
        System.out.println("2. Medium");
        System.out.println("3. Slow");

        int choice = scanner.nextInt();

        Ball ball;

        switch (choice) {
            case 1:
                ball = new FastBall(0, 0, 10, 10);
                break;
            case 2:
                ball = new MediumBall(0, 0, 10, 10);
                break;
            case 3:
                ball = new SlowBall(0, 0, 10, 10);
                break;
            default:
                ball = new MediumBall(0, 0, 10, 10);
                break;
        }

    
}}

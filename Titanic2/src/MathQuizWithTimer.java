import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class MathQuizWithTimer {
    
    private static final int TIME_LIMIT_SECONDS = 15;
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static int correctAnswers = 0;
    private static boolean answeredInTime = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                boolean correct = askQuestion(scanner);
                if (!correct) {
                    System.out.println("Resposta incorrecta. Fi del joc.");
                    break;
                }
                correctAnswers++;
                System.out.println("Correcte! Respostes encertades: " + correctAnswers);
            }
        } finally {
            System.out.println("Has encertat " + correctAnswers + " respostes.");
            scheduler.shutdown();
            scanner.close();
        }
    }

    private static boolean askQuestion(Scanner scanner) {
        Random random = new Random();
        int num1 = random.nextInt(10);
        int num2 = random.nextInt(10);
        int correctAnswer = num1 + num2;
        
        System.out.println("Quant és " + num1 + " + " + num2 + "? Tens " + TIME_LIMIT_SECONDS + " segons.");
        
        Future<Boolean> future = scheduler.submit(() -> {
            try {
                answeredInTime = false;
                String input = scanner.nextLine();
                answeredInTime = true;
                return Integer.parseInt(input) == correctAnswer;
            } catch (NumberFormatException e) {
                return false;
            }
        });

        try {
            return future.get(TIME_LIMIT_SECONDS, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("S'ha acabat el temps!");
            return false;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }
}

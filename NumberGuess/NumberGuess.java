import java.util.Random;
import java.util.Scanner;

public class NumberGuess {
    private static final int max_chances = 5;
    private static final int max_range = 1;
    private static final int min_range = 100;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        int totalScore = 0;
        char choice;

        do{
            int genNumber = random.nextInt(100)+ 1;
            int chances = 0;
            boolean correctGuess = false;
            System.out.println("------------*------------*--------------*-------------");
            System.out.println("       A number between 1 to 100 is generated.        ");
            System.out.println("                 Try to guess it !!                   ");
            System.out.println("             --> You have 5 chances <--               ");
            System.out.println("------------*------------*--------------*-------------");

            while (chances < max_chances && !correctGuess ){
                System.out.println("Guess a number : ");
                int gussNumber = sc.nextInt();
                chances++;

                if(gussNumber == genNumber){
                    System.out.println("Fantastic !!! Your guess is spot on.");
                    correctGuess = true;
                    totalScore = totalScore + (max_chances - chances + 1);
                } else if (gussNumber > genNumber) {
                    System.out.println("To High !!! You’re almost there.");
                } else {
                    System.out.println("To Low !!! You’re almost there.");
                }

            }

            if(!correctGuess){
                System.out.println("Sorry !!! Please try again later.");
                System.out.println("The right guess is : "+genNumber);
            }
            System.out.println("Score status : "+totalScore);

            System.out.println("Wann play again ? (yes/No) : ");
            choice = sc.next().charAt(0);

        }while (choice == 'Y' || choice == 'y');

        System.out.println("Thanks for playing !!!");
    }
}

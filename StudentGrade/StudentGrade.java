import java.util.Scanner;

public class StudentGrade {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        System.out.println("------------*-------------*------------*------------");
        System.out.println("               Sudent Grade Calculator              ");
        System.out.println("------------*-------------*------------*------------");
        System.out.println("Enter the number of Subjects : ");
        int sub = sc.nextInt();
        double marks []= new double[sub];
        double total=0;

        for (int i=0; i<sub; i++){
            System.out.println("Enter marks out of 100 for Subject "+(i+1)+" : ");
            marks[i]= sc.nextDouble();
            total= total+marks[i];
        }
        System.out.println("----------------------------------------------------");

        System.out.println("Total Marks out of "+(sub*100)+" : "+total);
        double avgPercent = AvgPercentageCalculate(total,sub);
        System.out.println("Avrage Percentage : "+avgPercent);
        String grade = GradeCalculate(avgPercent);
        System.out.println("Grade : "+grade);
        System.out.println("----------------------------------------------------");

    }

    public static double AvgPercentageCalculate(double total, double sub){
        double avgPercent = total/sub;
        return avgPercent;
    }

    public static String GradeCalculate(double avgPercent){
        if (avgPercent >= 90){
            return "A+";
        }
        else if (avgPercent >= 80){
            return "A";
        }
        else if (avgPercent >= 70){
            return "B+";
        }
        else if (avgPercent >= 60){
            return "B";
        }
        else if (avgPercent >= 50){
            return "C";
        }
        else if (avgPercent >= 40){
            return "D";
        }
        else {
            return "Fail";
        }
    }

}
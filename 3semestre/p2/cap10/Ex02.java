package cap10;

import java.util.Scanner;

public class Ex02 {
    public static void main(String[] args) {
        
        int n;
        Scanner s = new Scanner(System.in);

        n=s.nextInt();
        
        double[] year = new double[n];

        System.out.println(year);
        
        s.close();

        Temp c = new Temp();

        double tday = c.tempDay(year);

        System.out.println(tday);

    }
}

class Temp{

    public double average(double[] temps) {
        // Resolva aqui este exercício. 
        
        double media=0;
        
        for(int i = 0; i<temps.length;i++){
            media+=temps[i];
        }
        media /=temps.length;
        return media*100;
    }

    public int hot(double[] temps){
        int day=0;

        double max= temps[0];
        
        for(int i =1;i<temps.length;i++){
            
            if(temps[i]>max){
                
                day = i;
                
            }
        }

        return day;
    }

    public int cold(double[] temps){
        int day=0;

        double min= temps[0];
        
        for(int i =1;i<temps.length;i++){
            
            if(temps[i]<min){
                
                day = i;
                
            }
        }

        return day;
    }

    public double amplitude(double[] temps) {
        // Resolva aqui este exercício. 
        //find max and min
        double max= temps[0],min= temps[0];
        
        for(int i =1;i<temps.length;i++){
            
            if(temps[i]>max){
                
                max= temps[i];
                
            }else if(temps[i]<min){
                
                min= temps[i];
                
            }
        }
        return max-min;
    }

    public double tempDay(double[] temps){

        double temp;
        int day, month;

        Scanner s = new Scanner(System.in);

        int months[]= {31,28,31,30,31,30,31,31,30,31,30,31};

        System.out.println("data: ");

        day=s.nextInt();
        month = s.nextInt();

        while((day<1)||(month>13)||(month<1)||(months[month]!=day)){

            System.out.println("again: ");
            day=s.nextInt();
            month = s.nextInt();

        }

        s.close();

        int sum=0;

        for (int i=0;i<month;i++){
            sum+=months[i];
        }

        temp = temps[sum+day];

        return temp;
    }
}

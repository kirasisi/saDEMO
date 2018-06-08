

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.sin;


/**
 * This program using simulated annealing to search for the nearly optimal value of z
 * I have run the random value 1 million times to find the nearly optimal cooling rate, you can check the output chart for this step in PDF doc
 * you can also find pseudo code in PDF doc
 */
public class saDEMO {
    double T = 1; //initial temperature
    double alpha = 0.99;//Cooling rate
    double z = 0.0;
    double u = 0.0;
    double v = 0.0;
    ArrayList<Double> zList = new ArrayList<>();
    int iteration = 0;
    static public void main(String[] args)
    {
        saDEMO q = new saDEMO();
        q.chooseOption(q);

    }
    public void chooseOption(saDEMO q2){

        String choice = "";
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Choose the question you want to check:");
            System.out.println("1. Q2a");
            System.out.println("2. Q2b");
            System.out.println("5. EXIT");
            System.out.println("input 1,2,3,4 or 5:");

            choice  = sc.nextLine();
            switch(choice){
                case "1": q2.simulatedAnnealingA();//Question 2a
                    break;
                case "2": q2.simulatedAnnealingB();//Question 2b
                    break
                case "5": System.out.println("EXIT");
                    break;
                default: System.out.println("please follow the instruction and input a valid number");
                    break;
            }
        }
        while (!choice.equals("5"));

    }
//begin of Question 2a
    public double simulatedAnnealingA () {
        System.out.println("====simulating annealing for Q.2a====");
        System.out.println("Choose an option to initialize u:");
        System.out.println("1. u = 0");
        System.out.println("2. u = random number");
        System.out.println("3. input a number for u");
        String opt ="";

            z = 0;
            u = 0;
            T = 1;
            Scanner scA = new Scanner(System.in);
            opt = scA.nextLine();
            if(opt.equals("1")){
                u = 0;
            }
            else if(opt.equals("2")){
                u = randA(-1);
            }
            else if(opt.equals("3")){
                System.out.println("input u value:");
                Scanner scA3 = new Scanner(System.in);
                String uA3 = scA3.nextLine();
                double temp = Double.parseDouble(uA3);
                if(temp>=-1 && temp<=1){
                    u = temp;
                }
                else{
                    System.out.println("range: -1 <= u <= 1");
                    return simulatedAnnealingA();
                }
            }
            else{
                System.out.println("please enter a valid option");
                return simulatedAnnealingA();
            }
            z = calculateZa(u);

            int count = 0;
            zList.clear();
            while(T>=0.00001){ //start the loop until the temperature get to the threshold
                double randomU = randA(u); //select random u to calculate z
                double temp = calculateZa(randomU);
                if(z<temp){ //if z is smaller than the newly calculated number
                    u = randomU;
                    z = temp;//mutate the current z to the newly calculated z
                }
                else if(randomU<=Math.pow(Math.E, ((temp-z)/T))){ //else if random_u < = e^((new_z-current_z) /T):
                    u = randomU;
                    z = temp;//mutate the current z to the newly calculated z
                }
                //else not mutate
                T = alpha*T; //cooling the temperature in every iteration
                count = count +1;// this is used to record the iteration number (for drawing chart)
                zList.add(z);//used for drawing chart
                System.out.println("z: "+z+" ,u: "+u+" ,T: "+T);
            }
            iteration = count;
            System.out.println("Max z: "+z+" ,u: "+u+" ,T: "+T);

            return z;

        }

    public  double calculateZa(double randomU){ //create new z with random neighbour of u
        double zCurrent = 0.0;
        zCurrent = randomU*sin(1/(0.01 + Math.pow(randomU, 2))) + Math.pow(randomU, 3)*sin(1/(0.001 + Math.pow(randomU, 4)));

        return zCurrent;
    }

    public double randA(double u){
        try
        {
            if(u+0.2<1){
                u = ThreadLocalRandom.current().nextDouble(u,u+0.1);
            }
            else{
                u = ThreadLocalRandom.current().nextDouble(u,1);
            }

            DecimalFormat df = new DecimalFormat("#.#####");
            df.setRoundingMode(RoundingMode.CEILING);
            u = Double.parseDouble(df.format(u));
        }
        catch (Exception e){
            return u;
        }

        return u;
    }
// end of Question 2a

//begin of Question 2b
public double simulatedAnnealingB () {
    System.out.println("====simulating annealing for Q.2b====");
    System.out.println("Choose an option to initialize u , v :");
    System.out.println("1. u = 0, v = 0");
    System.out.println("2. u = random number,v = random number ");
    System.out.println("3. input a number for u,v");
    String opt ="";

    z = 0;
    u = 0;
    v = 0;
    T = 1;
    Scanner scB = new Scanner(System.in);
    opt = scB.nextLine();
    if(opt.equals("1")){
        u = 0;
        v = 0;
    }
    else if(opt.equals("2")){
        u = randUb(-1);
        v = randVb(-1);
    }
    else if(opt.equals("3")){
        System.out.println("input u value:");
        Scanner scB3 = new Scanner(System.in);
        String uB3 = scB3.nextLine();
        double tempU = Double.parseDouble(uB3);
        if(tempU>=-1 && tempU<=1){
            u = tempU;
        }
        else{
            System.out.println("range: -1 <= u <= 1");
            return simulatedAnnealingB();
        }
        System.out.println("input v value:");
        Scanner scB4 = new Scanner(System.in);
        String uB4 = scB4.nextLine();
        double tempV = Double.parseDouble(uB4);
        if(tempV>=-1 && tempV<=1){
            v = tempV;
        }
        else{
            System.out.println("range: -1 <= v <= 1");
            return simulatedAnnealingB();
        }
    }
    else{
        System.out.println("please enter a valid option");
        return simulatedAnnealingB();
    }
    z = calculateZb(u,v);

    int count = 0;
    zList.clear();
    while(T>=0.00001){
        // Random r = new Random();
        double randomU = randUb(u);
        double randomV = randVb(v);
        double temp = calculateZb(randomU,randomV);
        if(z<temp){
            u = randomU;
            v = randomV;
            z = temp;
        }
        else if(randomU<=Math.pow(Math.E, ((temp-z)/T))&&randomV<=Math.pow(Math.E, ((temp-z)/T))){
            u = randomU;
            v = randomV;
            z = temp;
        }
        T = alpha*T;
        count = count +1;
        zList.add(z);
        System.out.println("z: "+z+" ,u: "+u+" ,v: "+v+" ,T: "+T);
    }
    iteration = count;
    System.out.println("Max z: "+z+" ,u: "+u+" ,v: "+v+" ,T: "+T);

    return z;

}

    public  double calculateZb(double randomU,double randomV){ //create new z with random neighbour of u
        double zCurrent = 0.0;
        zCurrent =  randomU*Math.pow(randomV, 2)*sin(randomV/(0.01 + Math.pow(randomU, 2))) + Math.pow(randomU, 3)*Math.pow(randomV, 2)*sin(pow(randomV,3)/(0.001 + Math.pow(randomU, 4)));

        return zCurrent;
    }

    public double randUb(double u){
        try
        {
            if(u+0.1<1){
                u = ThreadLocalRandom.current().nextDouble(u,u+0.1);
            }
            else{
                u = ThreadLocalRandom.current().nextDouble(u,1);
            }
            DecimalFormat df = new DecimalFormat("#.#####");
            df.setRoundingMode(RoundingMode.CEILING);
            u = Double.parseDouble(df.format(u));
        }
        catch (Exception e){
            return u;
        }

        return u;
    }

    public double randVb(double v) {
        try {
            if(v+0.1<1){
                v = ThreadLocalRandom.current().nextDouble(v,v+0.1);
            }
            else{
                v = ThreadLocalRandom.current().nextDouble(v,1);
            }
            DecimalFormat df = new DecimalFormat("#.#####");
            df.setRoundingMode(RoundingMode.CEILING);
            v = Double.parseDouble(df.format(v));
        } catch (Exception e) {
            return v;
        }
        return v;
    }

//end of Question 2b



    public ArrayList returnZlist(){
        return zList;
    }


}





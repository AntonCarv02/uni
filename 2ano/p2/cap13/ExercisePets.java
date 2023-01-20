package cap13;

import java.util.ArrayList;
import java.util.Scanner;

public class ExercisePets {
    public static void main(String[] args) {
        ArrayList<Pet> lista = new ArrayList<Pet>();

        Scanner s = new Scanner(System.in);

        while (s.hasNext()) {
            String input = s.nextLine();

            if(input.length() < 2){
                System.out.println("ERR");
                continue;
            }

            char species = input.charAt(0);
            char spc = input.charAt(1);
            String name = input.substring(2);

            if ((Character.compare(species, 'd') == 0) && (Character.compare(spc, ' ') == 0)) {

                System.out.println("OK");
                Pet pet = new Dog(species, name);
                lista.add(pet);

            } else if ((Character.compare(species, 'c') == 0) && (Character.compare(spc, ' ') == 0)) {

                System.out.println("OK");
                Pet pet = new Cat(species, name);
                lista.add(pet);
            } else {
                System.out.println("ERR");
            }
        }
        System.out.println(lista.toString());
        s.close();
    }
}

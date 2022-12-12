package cap13;

import java.util.ArrayList;
import java.util.Scanner;

public class ExercicePets {
    public static void main(String[] args) {
        ArrayList<Pet> lista = new ArrayList<Pet>();

        Scanner s = new Scanner(System.in);

        
        String input = s.nextLine();

        char species = input.charAt(0);
        char spc = input.charAt(1);
        String name = input.substring(2);
        
        
        if(Character.compare(species, 'd')==0){

            Pet pet = new Pet(species, name);
            lista.add(pet);

        }else if(Character.compare(species, 'c')==0){

            Pet pet = new Cat(species, name);
            lista.add(pet);
        }

        
    }
}

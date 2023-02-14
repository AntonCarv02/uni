package cap10;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Ex18 {

    static void askuser() {
        System.out.println("Citar? S/N");
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        askuser();
        String in = s.nextLine();

        Quotation quote = new Quotation();

        int i;
        char input = verifInput(in, s);
        System.out.println(input);

        int comp = Character.compare(input, 'S');
        int comp2 = Character.compare(input, 'N');

        if ((comp == 0) && (comp2 != 0)) {

            while ((comp == 0) && (comp2 != 0)) {

                i = quote.random();
                System.out.println(quote.getquote(i));
                askuser();
                in = s.nextLine();

                input = verifInput(in, s);
                comp = Character.compare(input, 'S');
                comp2 = Character.compare(input, 'N');

            }
        }
        s.close();

    }

    public static char verifInput(String s, Scanner scanner) {

        char ans = s.charAt(0);

        int comp = Character.compare(ans, 'S');
        int comp2 = Character.compare(ans, 'N');

        while ((s.length() > 1) || ((comp != 0) && (comp2 != 0))) {
            System.out.println("Erro: " + s);
            askuser();
            s = scanner.nextLine();

            ans = s.charAt(0);

            comp = Character.compare(ans, 'S');
            comp2 = Character.compare(ans, 'N');
        }

        return ans;
    }
}

class Quotation {

    static ArrayList<String> quotes = new ArrayList<String>();

    public Quotation() {

        quotes.add("Ao céu faltam todas as pessoas interessantes.");
        quotes.add("Somente quem faz aprende.");
        quotes.add("Muitos teimam em seguir o caminho que escolheram, poucos o objetivo.");
        quotes.add("Quem luta com monstros deve acautelar-se para não se tornar um.");
        quotes.add("Enquanto olhas para o abismo, o abismo olha para ti.");

    }

    public int random() {
        Random rand = new Random();

        int valor = rand.nextInt(quotes.size());

        return valor;
    }

    public String getquote(int i) {

        String quote = quotes.get(i);

        return quote;
    }
}
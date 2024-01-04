import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class clientadmin {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static remoteObject obj;

    private static void menu() throws IOException {
        System.out.println("1 - Listar Artistas por estado");
        System.out.println("2 - Aprovar Artistas");
        System.out.println("0 - Sair");

        int option = -1;
        try {
            option = Integer.parseInt(br.readLine());
            if (option < 0 || option > 2) {
                System.out.println("Insira uma opção apresentada");
                menu();
            }
        } catch (NumberFormatException e) {
            System.err.println("Opção inválida!");
            menu();
        }
        switch (option) {
            case 0:
                System.exit(1);
                break;
            case 1:
                searchByState();
                break;
            case 2:
                updateState();
                break;

            default:
                menu();
        }
        menu();
    }

    private static void searchByState() throws java.rmi.RemoteException, IOException {

        String where = " WHERE approved=";

        System.out.println("estado a procurar: 0-not approved 1-approved");
        int state = Integer.parseInt(br.readLine());

        if (state == 0) {
            where = where + false;
        } else if (state == 1) {
            where = where + true;

        }
        List<artist> results = obj.searchTableArtist(where);

        if (results.size() > 0) {
            for (artist artist : results)
                System.out.println("id: " + artist.getArtistID());
        } else
            System.out.println("Nenhum artista!");
    }

    private static void updateState() throws java.rmi.RemoteException, IOException {

        List<artist> showNonApproved = obj.searchTableArtist("where approved=" + false);

        if (showNonApproved.size() > 0) {
            for (artist artist : showNonApproved)
                System.out.println("id: " + artist.getArtistID());


        } else {
            System.out.println("Nenhum artista por aprovar!"); 
            return ;
        }

        System.out.println("id do artista a aprovar: ");

        int where = Integer.parseInt(br.readLine());
        System.out.println(where);

        obj.changeState(true, where);
        

        
    }

    public static void main(String[] args) {
        String regHost = "localhost";
        String regPort = "9000";

        if (args.length != 2) {
            System.out.println("Faltam argumentos!");
            System.exit(1);
        }

        regHost = args[0];
        regPort = args[1];
        try {
            obj = (remoteObject) java.rmi.Naming.lookup("rmi://" + regHost + ":" + regPort + "/remoteobject");

            System.out.println("Bem vindo ao Sistema de Artistas de Rua do Alentejo!");
            System.out.println("");

            menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

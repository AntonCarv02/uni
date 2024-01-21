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
        System.out.println("3- Atualizar Informação Artistas");
        System.out.println("4 - Atualizar Estado de Utilizador");
        System.out.println("0 - Sair");

        int option = -1;
        try {
            option = Integer.parseInt(br.readLine());
            if (option < 0 || option > 4) {
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
                updateArtistState();
                break;
            case 3:
                updateArtistInfo();
                break;
            case 4:
                updateUserState();
                break;

            default:
                menu();
        }
        menu();
    }

    private static void searchByState() throws java.rmi.RemoteException, IOException {

        String where = " WHERE approved=";
        int state = 999;
        do {
            System.out.println("estado a procurar: 0-not approved 1-approved");
            try {
                state = Integer.parseInt(br.readLine());
                if (state == 0) {
                    where = where + false;
                } else if (state == 1) {
                    where = where + true;
                }
                break;
            } catch (NumberFormatException e) {
                System.err.println("Insira uma opção valida");
                continue;
            }
        } while (state != 0 || state != 1);
        List<artist> results = obj.searchTableArtist(where);

        if (results.size() > 0) {
            for (artist artist : results)
                System.out.println("id: " + artist.getArtistID() + " ,Nome: " + artist.getName());
        } else
            System.out.println("Nenhum artista!");
    }

    private static void updateArtistState() throws java.rmi.RemoteException, IOException {

        List<artist> showNonApproved = obj.searchTableArtist("where approved=" + false);

        if (showNonApproved.size() > 0) {
            for (artist artist : showNonApproved)
                System.out.println("id: " + artist.getArtistID() + " ,Nome: " + artist.getName());
        } else {
            System.out.println("Nenhum artista por aprovar!");
            return;
        }
        int where;
        do {
            System.out.println("Insira o ID do artista a aprovar: ");
            try {
                where = Integer.parseInt(br.readLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Insira um ID valido(Númerico)");
                continue;
            }
        } while (true);
        obj.changeArtistState("approved='true'", where);
    }

    private static void updateUserState() throws java.rmi.RemoteException, IOException {
        String username = "";

        do {
            System.out.println("Insira o Username do utilizador a receber Administrador: ");
            username = br.readLine().trim();
        } while (username == "");
        List<user> showUser = obj.searchTableUser("where type='USER' AND username='" + username + "'");
        if (showUser.size() > 0) {
            obj.changeUserState(username);
            System.out.println("Utilizador: " + username + " , Agora é administrador ");

        } else {
            System.out.println("Nenhum Utilizador com esse username!");
        }
    }

    private static void updateArtistInfo() throws java.rmi.RemoteException, IOException {
        int id;
        do {
            System.out.println("Insira o ID do Artista que quer Consultar: ");
            try {
                id = Integer.parseInt(br.readLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Insira um ID valido(Númerico)");
                continue;
            }
        } while (true);

        List<artist> showArtist = obj.searchTableArtist("where id=" + id);
        if (showArtist.size() > 0) {
            for (artist artist : showArtist) {
                System.out.println("Artista - " + artist.getArtistID());
                System.out.println("Nome - " + artist.getName());
                System.out.println("Tipo de Arte - " + artist.getTypeArt());
                System.out.println("Localização - (" + artist.getLatitude() + ", " + artist.getLongitude() + ")");
                System.out.println("Performing - " + artist.getPerforming());
                menuArtistChanges(artist.getArtistID());
            }
        } else {
            System.out.println("Nenhum Artista com esse ID!");
        }
    }

    private static void menuArtistChanges(int artistID) throws IOException {
        System.out.println("1 - Mudar Nome");
        System.out.println("2 - Mudar Tipo de arte");
        System.out.println("3 - Mudar Localização");
        System.out.println("4 - Mudar a Atuar");
        System.out.println("0 - Sair");

        int option = -1;
        try {
            option = Integer.parseInt(br.readLine());
            if (option < 0 || option > 4) {
                System.out.println("Insira uma opção apresentada");
                menuArtistChanges(artistID);
            }
        } catch (NumberFormatException e) {
            System.err.println("Opção inválida!");
            menuArtistChanges(artistID);
        }
        String set = "";
        switch (option) {
            case 0:
                break;
            case 1:
                String name = "";
                do {
                    System.out.print("Insira o nome do Artista: ");
                    name = br.readLine();
                    name = name.trim();
                } while (name.length() == 0);
                set = "name='" + name + "'";
                obj.changeArtistState(set, artistID);
                break;
            case 2:
                String typeart = "";
                do {
                    System.out.print("Insira o Tipo de Arte: ");
                    typeart = br.readLine();
                    typeart = typeart.toLowerCase();
                    typeart = typeart.trim();
                } while (typeart.length() == 0);
                set = "type=" + typeart;
                obj.changeArtistState(set, artistID);
                break;
            case 3:
                double lat_int;
                do {
                    System.out.println("Insira a latitude do artista: ");
                    try {
                        lat_int = Double.parseDouble(br.readLine().trim());
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Insira uma Latitude válida");
                        continue;
                    }
                } while (true);
                double long_int;
                do {
                    System.out.println("Insira a longitude do artista: ");
                    try {
                        long_int = Double.parseDouble(br.readLine().trim());
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Insira uma Longitude válida");
                        continue;
                    }
                } while (true);
                set = "latitude=" + lat_int;
                obj.changeArtistState(set, artistID);
                set = "longitude=" + long_int;
                obj.changeArtistState(set, artistID);
                break;
            case 4:
                Boolean performing = false;
                do {
                    System.out.println("O Artista está a atuar?");
                    System.out.println("1 - Sim ");
                    System.out.println("0 - Não ");
                    try {
                        int opt = Integer.parseInt(br.readLine());
                        if (opt == 1) {
                            performing = true;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Insira uma opção valida");
                        continue;
                    }
                } while (true);
                set = "performing=" + performing;
                obj.changeArtistState(set, artistID);
                break;
            default:
                menuArtistChanges(artistID);
        }
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

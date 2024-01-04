import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class client {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static remoteObject obj;

    private static void menu() throws IOException {
        System.out.println("1 - Registar um Artista");
        System.out.println("2 - Listar Artistas");
        System.out.println("3 - Listar Atuações");
        System.out.println("4 - Enviar um Donativo");
        System.out.println("5 - Listar Donativos");
        System.out.println("0 - Sair");

        int option = -1;
        try {
            option = Integer.parseInt(br.readLine());
            if (option < 0 || option > 5) {
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
                registerArtist();
                break;
            case 2:
                searchArtists();
                break;
            case 3:
                searchByLocations();
                break;
            case 4:
                registerDonative();
                break;
            case 5:
                searchDonative();
                break;
            default:
                menu();
        }
        menu();
    }

    private static void registerArtist() throws IOException {
        artist artist = new artist();

        String name = "";
        do {
            System.out.print("Insira o nome do Artista: ");
            name = br.readLine();
            name = name.trim();
        } while (name.length() == 0);
        artist.setName(name);

        String typeart = "";
        do {
            System.out.print("Insira o Tipo de Arte: ");
            typeart = br.readLine();
            typeart = typeart.toLowerCase();
            typeart = typeart.trim();
        } while (typeart.length() == 0);
        artist.setTypeArt(typeart);

        String local = "";
        do {
            System.out.print("Insira a localização do artista: ");
            local = br.readLine();
            local = local.trim();
        } while (local.length() == 0);
        artist.setLocation(local);

        Boolean performing = false;
        do {
            System.out.print("O Artista está a atuar?");
            System.out.print("1 - Sim ");
            System.out.print("0 - Não ");
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
        artist.setPerforming(performing);

        Boolean approved = false;
        artist.setApproved(approved);

        int id = obj.sendArtist(artist);
        artist.setArtistID(id);

        System.out.println("Artista " + id + " inserido!");
    }

    private static void searchArtists() throws java.rmi.RemoteException, IOException {

        System.out.println("Filtrar por localização? Sim/Nao");
        String where = "WHERE ";
        boolean first = userChoice(br.readLine());
        if (first) {
            System.out.println("Inserir cidade: ");
            where = where + "local='" + br.readLine().trim() + "'";
        }

        System.out.println("Filtrar por Tipo de arte? Sim/Nao");
        boolean second = userChoice(br.readLine());

        if (first == true && second == true) {
            System.out.println("Inserir Tipo de Arte: ");
            where = where + " and type='" + br.readLine().trim() + "'";

        } else if (first == false && second == true) {
            System.out.println("Inserir Tipo de Arte: ");
            where = where + "type='" + br.readLine().trim() + "'";
        }

        System.out.println(where);
        List<artist> results = obj.searchTableArtist(where);

        if (results.size() > 0) {
            for (artist artist : results)
                System.out.println("id: " + artist.getArtistID() + "- " + artist.getName());
        } else
            System.out.println("Nenhum artista a Atuar!");
    }

    private static boolean userChoice(String line) {
        do {
            if (line.equalsIgnoreCase("sim")) {
                return true;
            } else if (line.equalsIgnoreCase("não")) {
                return false;
            }
            System.out.println("input Invalido. escrever 'sim' ou 'não'.");

        } while (!line.equalsIgnoreCase("sim") || !line.equalsIgnoreCase("não"));
        return false;
    }

    private static void searchByLocations() throws java.rmi.RemoteException, IOException {

        String where = " WHERE performing=true";
        List<artist> results = obj.searchTableArtist(where);
        List<String> locals = new ArrayList<String>();
        if (results.size() > 0) {
            for (artist artist : results) {
                if (!locals.contains(artist.getLocation())) {
                    locals.add(artist.getLocation());
                }
            }
            for (String local : locals) {
                System.out.println(local);
            }
        } else
            System.out.println("Nenhum artista a atuar neste momento!");
    }

    private static void registerDonative() throws java.rmi.RemoteException, IOException {
        donative donative = new donative();

        String name = "";
        do {
            System.out.print("Insira o seu Nome: ");
            name = br.readLine();
            name = name.trim();
        } while (name.length() == 0);
        donative.setDonatorname(name);

        int artistid = -1;
        List<artist> artists = obj.searchTableArtist(" ");

        if (artists.size() > 0) {
            for (artist artist : artists)
                System.out.println("id: " + artist.getArtistID());
        }

        do {
            System.out.print("Insira o ID do Artista que pretende Doar: ");
            try {
                artistid = Integer.parseInt(br.readLine());
                if (artistid > 0)
                    break;
            } catch (NumberFormatException e) {
                System.err.println("Formato inválido! Insira um número inteiro positivo!");
                continue;
            }
        } while (true);
        donative.setArtistID(artistid);

        int value = -1;
        do {
            System.out.print("Insira o valor que pretende doar: ");
            try {
                value = Integer.parseInt(br.readLine());
                if (value > 0)
                    break;
            } catch (NumberFormatException e) {
                System.err.println("Formato inválido! Insira um número inteiro positivo!");
                continue;
            }
        } while (true);
        donative.setValue(value);

        int id = obj.sendDonative(donative);
        donative.setDonativeID(id);

        System.out.println("Valor doado com sucesso - Id de doação: " + id);
    }



    private static void searchDonative() throws java.rmi.RemoteException, IOException {

        String where = " WHERE artistid='";

        int artistid = -1;
        do {
            System.out.print("Insira o ID do Artista: ");
            try {
                artistid = Integer.parseInt(br.readLine());
                if (artistid > 0)
                    break;
            } catch (NumberFormatException e) {
                System.err.println("Formato inválido! Insira um número inteiro positivo!");
                continue;
            }
        } while (true);
        where = where + artistid + "'";

        List<donative> results = obj.searchTableDonatives(where);
        if (results.size() > 0) {
            for (donative donative : results) {
                System.out.println("Valor - " + donative.getValue() + " doado para Artista com ID- "
                        + donative.getArtistID() + " por " + donative.getDonatorname());
            }
        } else
            System.out.println("Nenhum Donativo para este Artista!");
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

            System.out.println("Bem vindo ao Sistema de Artistas de Rua do Alentejo!\n");

            menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.trabalho2;
import java.io.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;



import java.time.LocalDate;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class client {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static remoteObject obj;
    private static user user;

    private static void menu() throws IOException {
        System.out.println("0 - Sair");
        System.out.println("1 - Registar um Artista");
        System.out.println("2 - Listar Artistas");
        System.out.println("3 - Listar Atuações");
        System.out.println("4 - Enviar um Donativo");
        System.out.println("5 - Listar Donativos");
        System.out.println("6 - Avaliar um Artista");
        System.out.println("7 - Procurar Atuações Antigas");
        System.out.println("8 - Procurar Atuações Futuras");

        if (user.getType().equals("ADMIN")) {
            System.out.println("9 - Listar Artistas por estado");
            System.out.println("10 - Aprovar Artistas");
            System.out.println("11- Atualizar Informação Artistas");
            System.out.println("12 - Atualizar Estado de Utilizador");
        }

        int option = -1;
        try {
            option = Integer.parseInt(br.readLine());

            if ((option < 0 || option > 8) && (user.getType().equals("USER"))) {
                System.out.println("Insira uma opção apresentada");
                menu();

            } else if ((option < 0 || option > 12) && (user.getType().equals("ADMIN"))) {
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
            case 6:
                registerRating();
                break;
            case 7:
                searchPrevPerformances();
                break;
            case 8:
                searchNextPerformances();
                break;
            case 9:
                searchByState();
                break;
            case 10:
                updateArtistState();
                break;
            case 11:
                updateArtistInfo();
                break;
            case 12:
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
        obj.changeArtistState("approved=true", where);
    }

    private static void registerRating() throws RemoteException {

        String username = user.getUsername();

        int artistid = -1;
        List<artist> artists = obj.searchTableArtist(" ");

        if (artists.size() > 0) {
            for (artist artist : artists)
                System.out.println("id: " + artist.getArtistID());
        } else {
            System.out.println("nenhum artista");
            ;
        }
        boolean found = false;
        do {
            System.out.print("Insira o ID do Artista que pretende avaliar: ");
            try {
                artistid = Integer.parseInt(br.readLine());

                for (artist a : artists) {
                    if (a.getArtistID() == (artistid)) {
                        found = true;
                    }
                }

            } catch (NumberFormatException | IOException e) {
                System.err.println("Formato inválido! Insira um número inteiro positivo!");
                continue;
            }
        } while (!found);

        int value = -1;
        do {
            System.out.print("Insira a sua avaliaçao (0-5): ");
            try {
                value = Integer.parseInt(br.readLine());
                if (value > 0 && value < 6)
                    break;
            } catch (NumberFormatException | IOException e) {
                System.err.println("Formato inválido! Insira um número inteiro positivo!");
                continue;
            }
        } while (true);

        rating avaliacao = new rating(artistid, username, value);

        int id = obj.sendRating(avaliacao);

        System.out.println("Valor doado com sucesso - Id de doação: " + id);

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
        artist.setLatitude(lat_int);

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
        artist.setLongitude(long_int);

        Boolean performing = false;
        do {
            System.out.println("O Artista está a atuar? s/n");

            try {

                if (userChoice(br.readLine())) {
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
        int id = -1;
        String where = "name='" + artist.getName() + "'";
        List<artist> lista = obj.searchTableArtist(where);

        LocalDate today = LocalDate.now();
        Date date = Date.valueOf(today);

        if (lista.size() > 0) {
            for (artist a : lista) {
                where = "longitude=" + artist.getLongitude() + " AND latitude=" + artist.getLatitude()
                        + " AND actuation_date=" + date;
                List<performances> perf = obj.searchTablePerformance(where);
                if (perf.size() <= 0) {
                    performances p = new performances();
                    p.setArtist_id(a.getArtistID());
                    p.setLatitude(artist.getLatitude());
                    p.setLongitude(artist.getLongitude());
                    p.setperformance_date(date);
                    obj.sendPerformance(p);
                }
                String set = "latitude=" + artist.getLatitude() + ",  longitude=" + artist.getLongitude();
                obj.changeArtistState(set, a.getArtistID());
                System.out.println("Artista " + id + "já existe, localização foi atualizada!");
            }
        } else {
            id = obj.sendArtist(artist);
            artist.setArtistID(id);
            System.out.println("Artista " + id + " inserido!");
            performances p = new performances();
            p.setArtist_id(artist.getArtistID());
            p.setLatitude(artist.getLatitude());
            p.setLongitude(artist.getLongitude());
            p.setperformance_date(date);
            obj.sendPerformance(p);
        }
    }

    private static void searchArtists() throws java.rmi.RemoteException, IOException {

        System.out.println("Filtrar por localização? s/n");
        String where = "";
        boolean first = userChoice(br.readLine());

        if (first) {
            double lat_int;
            do {
                System.out.println("Insira a latitude: ");
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
                System.out.println("Insira a longitude: ");
                try {
                    long_int = Double.parseDouble(br.readLine().trim());
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Insira uma Longitude válida");
                    continue;
                }
            } while (true);
            where = "WHERE " + "latitude=" + lat_int + "AND longitude=" + long_int;
        }

        System.out.println("Filtrar por Tipo de arte? s/n");
        boolean second = userChoice(br.readLine());

        if (first == true && second == true) {
            System.out.println("Inserir Tipo de Arte: ");
            where = where + " and type='" + br.readLine().trim() + "'";

        } else if (first == false && second == true) {
            System.out.println("Inserir Tipo de Arte: ");
            where = "WHERE " + "type='" + br.readLine().trim() + "'";
        }

        System.out.println(where);

        List<artist> results = obj.searchTableArtist(where);

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        if (results.size() > 0) {
            for (artist artist : results) {

                double ratingArtist = obj.ArtistRating(artist.getArtistID());
                String ratString = decimalFormat.format(ratingArtist);

                System.out.println(
                        "id: " + artist.getArtistID() + "- " + artist.getName() + ", avaliação:" + ratString);
            }
        } else
            System.out.println("Nenhum artista a Atuar!");
    }

    private static boolean userChoice(String line) {
        do {
            if (line.equalsIgnoreCase("s")) {
                return true;
            } else if (line.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("input Invalido. escrever 's' ou 'n'.");
            try {
                line = br.readLine();
            } catch (IOException e) {

                System.out.println("input Invalido. escrever 's' ou 'n'.");
            }

        } while (!line.equalsIgnoreCase("s") || !line.equalsIgnoreCase("n"));
        return false;
    }

    private static void searchByLocations() throws java.rmi.RemoteException, IOException {

        String where = " WHERE performing=true";
        List<artist> results = obj.searchTableArtist(where);
        List<String> locals = new ArrayList<String>();
        if (results.size() > 0) {
            for (artist artist : results) {
                if ((!locals.contains(artist.getLatitude() + " " + artist.getLongitude()))) {
                    locals.add(artist.getLatitude() + " " + artist.getLongitude());
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
        } else {
            System.out.println("nenhum artista");
            ;
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
        LocalDate today = LocalDate.now();
        Date date = Date.valueOf(today);
        donative.setDate(date);

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
                        + donative.getArtistID() + " por " + donative.getDonatorname() + " na data- "
                        + donative.getDonation_date());
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

            loginmenu();
            menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loginmenu() {

        System.out.println("0 - Sair");
        System.out.println("1 - Login");
        System.out.println("2 - Registar");
        boolean permission = false;
        int option = -1;
        try {
            option = Integer.parseInt(br.readLine());
            if (option < 1 || option > 2) {
                System.out.println("Insira uma opção apresentada");
                loginmenu();
            }
        } catch (NumberFormatException | IOException e) {
            System.err.println("Opção inválida!");
            loginmenu();
        }

        switch (option) {
            case 0:
                System.exit(1);
                break;
            case 1:
                permission = MakeLogin();
                break;
            case 2:
                registerUser();
                break;
            default:
                loginmenu();
        }
        if (!permission)
            loginmenu();
    }

    private static void registerUser() {
        String user = new String(), pass = new String(), email = new String();
        List<user> list = null;
        try {
            do {
                System.out.println("username:");
                user = br.readLine();
                list = obj.searchTableUser("where username='" + user + "'");
                if (!list.isEmpty()) {
                    System.out.println("username ja existe!");
                    System.out.println(list);
                }
            } while (!list.isEmpty());

            System.out.println("pass:");
            pass = hashPassword(br.readLine());
            System.out.println("email:");
            email = br.readLine();

            user newu = new user(user, email, pass, "USER");

            System.out.println("User criado com o user name: " + obj.sendUser(newu));

        } catch (RemoteException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean MakeLogin() {
        String username = new String(), pass = new String();
        List<user> l = null;

        System.out.println("login:");
        try {
            do {

                System.out.println("username:");
                username = br.readLine();
                System.out.println("pass:");
                pass = hashPassword(br.readLine());

                l = obj.searchTableUser("where username='" + username + "' AND password='" + pass + "'");

                if (l.isEmpty()) {
                    System.out.println("credenciais erradas! pretende fazer registo? s ou n");

                    String resp = br.readLine();

                    if (userChoice(resp)) {
                        return false;
                    }
                } else
                    user = l.get(0);

            } while (l.isEmpty());

        } catch (RemoteException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception, for example, log an error message or terminate the
            // program.
            e.printStackTrace();
            return null;
        }
    }

    private static void searchPrevPerformances() throws java.rmi.RemoteException, IOException {

        String where = " WHERE artist_id='";

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

        where += artistid + "'";

        LocalDate today = LocalDate.now();
        Date date = Date.valueOf(today);

        where += " AND actuation_date<'" + date + "'";

        List<performances> results = obj.searchTablePerformance(where);

        if (results.size() > 0) {
            for (performances performances : results) {
                System.out.println("local - LAT-" + performances.getLatitude() + "/LONG-" + performances.getLongitude()
                        + "; Data:" + performances.getperformance_date());
            }
        } else
            System.out.println("Nenhuma atuação anterior a hoje para este Artista!");
    }

    private static void searchNextPerformances() throws java.rmi.RemoteException, IOException {

        String where = " WHERE artist_id='";

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

        where += artistid + "'";

        LocalDate today = LocalDate.now();
        Date date = Date.valueOf(today);

        where += " AND actuation_date>'" + date + "'";

        List<performances> results = obj.searchTablePerformance(where);

        if (results.size() > 0) {
            for (performances performances : results) {
                System.out.println("local - LAT-" + performances.getLatitude() + "/LONG-" + performances.getLongitude()
                        + "; Data:" + performances.getperformance_date());
            }
        } else
            System.out.println("Nenhuma atuação futura para este Artista!");
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
                set = "type='" + typeart + "'";
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

}
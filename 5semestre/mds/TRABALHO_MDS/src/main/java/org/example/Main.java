package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.example.model.Acao;
import org.example.model.Clinica;
import org.example.model.Consulta;
import org.example.model.Profissional;
import org.example.model.User;

public class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String[] args) {
        Acao acao = new Acao(new ArrayList<Profissional>(),
		 new ArrayList<User>(),
		 new ArrayList<Consulta>(),
		new ArrayList<Clinica>());
        System.out.println("BEM-VINDO SAÚDE EM CASA -ÉVORA!!!");
        loginmenu( acao);
        menu(acao);

    }

    private static void loginmenu(Acao acao) {

        System.out.println("0 - Sair");
        System.out.println("1 - Login");
        System.out.println("2 - Registar Cliente");
        System.out.println("3 - Registar Profissional");
        boolean permission = true;
        int option = -1;
        try {
            option = Integer.parseInt(br.readLine());
            if (option < 0 || option > 3) {
                System.out.println("Insira uma opção apresentada");
                loginmenu(acao);
            }
        } catch (NumberFormatException | IOException e) {
            System.err.println("Opção inválida!");
            loginmenu(acao);
        }

        switch (option) {
            case 0:
                System.exit(1);
                break;
            case 1:
                break;
            case 2:
                acao.registerUser();
                break;
            case 3:
                acao.registerProf();
                break;
            default:
                loginmenu( acao);
        }
        if (!permission)
            loginmenu( acao);
    }

    
    private static void menu(Acao acao) {
        System.out.println("Escolha uma opção:\n");

        System.out.println("0 - Sair");
        System.out.println("1 - Pesquisar Serviços");
        System.out.println("2 - Apresenta Serviço");
        System.out.println("3 - Agendar Consulta");

        System.out.println("-------ADMIN-------");
        System.out.println("4 - Aprovar Clientes");
        System.out.println("5 - Aprovar Profissionais");
        System.out.println("6 - Remover Profissional");

        int option = -1;
        try {
            option = Integer.parseInt(br.readLine());

            if ((option < 0 || option > 8)) {
                System.out.println("Insira uma opção apresentada");
                menu(acao);

            }

        } catch (NumberFormatException | IOException e) {
            System.err.println("Opção inválida!");
            menu(acao);
        }
        switch (option) {
            case 0:
                System.exit(1);
                break;
            case 1:
                acao.apresentarServico();
                break;
            case 2:
                acao.PesquisarServicos();
                break;
            case 3:
                acao.agendarconsulta();
                break;
            case 4:
                acao.AprovarCliente();
                break;
            case 5:
                acao.aprovarProf();
                break;
            case 6:
                acao.RemoverProf();
                break;
            default:
                menu(acao);
        }

        menu(acao);
    }

}
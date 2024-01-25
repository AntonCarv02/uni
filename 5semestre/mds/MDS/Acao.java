package org.example.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Acao {

	private List<Profissional> profissionais;
	private List<User> user;
	private List<Consulta> consultas;
	private List<Clinica> clinica;
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public List<Profissional> getProfissionais() {
		return profissionais;
	}

	public void setProfissionais(List<Profissional> profissionais) {
		this.profissionais = profissionais;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

	public List<Clinica> getClinica() {
		return clinica;
	}

	public void setClinica(List<Clinica> clinica) {
		this.clinica = clinica;
	}

	public BufferedReader getBr() {
		return br;
	}

	public void setBr(BufferedReader br) {
		this.br = br;
	}

	public Acao(List<Profissional> prof,
			List<User> us,
			List<Consulta> cons,
			List<Clinica> clin) 
	{

		this.profissionais = prof;
		this.user = us;
		this.consultas= cons;
		this.clinica = clin;

		profissionais.add(new Profissional("Andre", "Médico", "Masculino", false, "Cardiologia", 20, "14-03-2023", "medicare"));
		user.add(new User("Pedro", "Medicare", "Adulto", "User", false));
		clinica.add(new Clinica("CLINICA Joelho", false));

	}

	public Acao() {
		this.profissionais = new ArrayList<Profissional>();
		this.user = new ArrayList<User>();
		this.consultas=new ArrayList<Consulta>() ;
		this.clinica = new ArrayList<Clinica>();
		
    }

    public void registerProf() {

		try {
			System.out.println("Insira os dados: Nome\n");
			String nome = br.readLine();

			System.out.println("Insira o seu género");
			String genero = br.readLine();

			System.out.println("Insira a sua função");
			String func = br.readLine();

			System.out.println("Insira a sua especialidade");
			String esp = br.readLine();

			System.out.println("Insira a data disponivel(YYYY-MM-DD)");
			String data = br.readLine();

			System.out.println("Insira o seu seguro de saúde");
			String seguro = br.readLine();

			System.out.println("Insira o seu preco");
			int preco = Integer.parseInt(br.readLine());

			Profissional prof = new Profissional(nome, func, genero, false, esp, preco, data, seguro);
			profissionais.add(prof);
			System.out.println("Profissional registado com sucesso!!!");

		} catch (IOException e) {
			System.out.println("Input inválido");

			e.printStackTrace();
		}

	}

	public void registerUser() {

		try {
			System.out.println("Insira os dados: Nome\n");
			String nome = br.readLine();

			System.out.println("Insira o seu seguro de sáude");
			String seguro = br.readLine();
			System.out.println("Insira o seu género");
			String genero = br.readLine();
			User users = new User(nome, seguro, genero, "USER", false);
			user.add(users);
			System.out.println("Utilizador registado com sucesso!!!");

		} catch (IOException e) {
			System.out.println("Input inválido");
			registerUser();
			e.printStackTrace();
		}

	}

	public void RemoverProf() {
		for (int x = 0; x < profissionais.size(); x++) {
			System.out.println(profissionais.get(x).getNome());
		}
		System.out.println("Qual o profissional que quere remover?");
		String nome = new String();
		try {
			nome = br.readLine();
		} catch (IOException e) {

			System.out.println("Input inválido\n");
			e.printStackTrace();
		}

		for (int a = 0; a < profissionais.size(); a++) {
			if (nome.equals(profissionais.get(a).getNome())) {
				profissionais.remove(a);
				System.out.println("Profissional removido com sucesso!!\n");
			}
		}

	}

	public void aprovarProf() {

		for (int i = 0; i < profissionais.size(); i++) {
			if (profissionais.get(i).getAprovado().equals(false)) {
				System.out.println(profissionais.get(i).getNome() + "\n");
			}
		}

		System.out.println("Qual o profissional que quer aprovar?: ");
		String nome;
		try {
			nome = br.readLine();
			for (int a = 0; a < profissionais.size(); a++) {
				if (profissionais.get(a).getNome().equals(nome)) {
					boolean aprovado = true;
					profissionais.get(a).setAprovado(aprovado);
					System.out.println("Profissional aprovado com sucesso!!!");

				}
			}
		} catch (IOException e) {

			System.out.println("Input inválido");
			e.printStackTrace();
		}

	}

	public void AprovarCliente() {

		for (int i = 0; i < user.size(); i++) {
			if (user.get(i).getAprovado().equals(false)) {
				System.out.println(user.get(i).getNome() + "\n");
			}
		}

		System.out.println("Qual o cliente que quer aprovar?: ");
		String nome;
		try {
			nome = br.readLine();
			for (int a = 0; a < user.size(); a++) {
				if (user.get(a).getNome().equals(nome)) {
					boolean aprovado = true;
					user.get(a).setAprovado(aprovado);
					System.out.println("Cliente aprovado com sucesso!!!");

				} 
			}
		} catch (IOException e) {

			System.out.println("Input inválido");
			e.printStackTrace();
		}

	}

	public void PesquisarServicos() {

		// apresentar o profissional e a especialidade
		for (int i = 0; i < profissionais.size(); i++) {
			System.out.println("nome do medico: " + profissionais.get(i).getNome() + " especialidade: "
					+ profissionais.get(i).getEspecialidade() + " Disponibilidade: " + profissionais.get(i).getData());

		}

		agendarconsulta();

	}

	// mostrar para um certo profissional os seus serviços não aprovados e
	// aprovados. Também conseguirá aprova los
	public void apresentarServico() {
		System.out.println("Qual o nome do profissional: ");
		String nome = new String();
		try {
			nome = br.readLine();
		} catch (IOException e) {
			System.out.println("Input inválido");
			e.printStackTrace();
		}

		boolean check = false;
		for (int i = 0; i < profissionais.size(); i++) {
			if (nome.equals(profissionais.get(i).getNome())) {
				check = true;
			}
		}

		if (check) {
			for (int a = 0; a < consultas.size(); a++) {
				if (nome.equals(consultas.get(a).getNome_prof())) {
					System.out.println(
							consultas.get(a).getNome_cliente() + " | " + consultas.get(a).getAprovado() + "\n");
				}

			}

			System.out.println("Qual o nome do cliente da consulta que quer aprovar?");
			String nome_cliente = new String();

			try {
				nome_cliente = br.readLine();
			} catch (IOException e) {
				System.out.println("Input inválido");

				e.printStackTrace();
			}

			for (int b = 0; b < consultas.size(); b++) {
				if (nome_cliente.equals(consultas.get(b).getNome_cliente())) {
					boolean aprovado = true;
					consultas.get(b).setAprovado(aprovado);
					System.out.println("Consulta aprovada com sucesso!!!");
				} else {
					System.out.println("Esse cliente não tem consultas agendadas para o profissional de saúde");
				}
			}
		}

	}

	public void agendarconsulta() {

		System.out.println("Insira o seu nome: ");
		String nome_cliente = null;
		try {
			nome_cliente = br.readLine();
		} catch (IOException e) {
			System.out.println("Input inválido");
			e.printStackTrace();
		}

		for (int a = 0; a < user.size(); a++) {
			if (nome_cliente.equals(user.get(a).getNome())) {
				break;
			} else {
				System.out.println("Nome não existente");
				agendarconsulta();
			}
		}

		try {
			System.out.println("Insira o nome do profissional: ");
			String nome = br.readLine();
			System.out.println("Insira a data no formato (DD-MM-YYYY)");
			String data = br.readLine();

			Consulta consulta = null;
			for (int i = 0; i < profissionais.size(); i++) {
				if (nome.equals(profissionais.get(i).getNome())) {
					if (profissionais.get(i).getAprovado().equals(true)) {
						if (profissionais.get(i).getData().equals(data)) {
							consulta = new Consulta(nome_cliente, nome, data, false);
							consultas.add(consulta);
							System.out.println("Consulta registada com sucesso!!!");
							break;
						} else if (!profissionais.get(i).getData().equals(data)) {
							System.out.println("Profissional não tem dispinobilidade");
							agendarconsulta();
						}
					}
				}
			}

		} catch (IOException e) {
			System.out.println("Input inválido");

			e.printStackTrace();
		}

	}
}
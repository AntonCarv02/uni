package org.example.model;


public class Admin {
	
	
/*/
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

*/

}
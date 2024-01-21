package com.example.servingwebcontent;

import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	@PostMapping("/atendimento")
	public String atendimento(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		System.out.println("RECEBEMOS: " + name);
		return "greeting"; // nome do template usado para processar a vista devolvida
	}

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			@RequestParam(name = "cidade", required = false, defaultValue = "Paris") String cidade,
			Model model,
			HttpSession session) {
		model.addAttribute("name", name);
		model.addAttribute("cidade", cidade);
		
		System.out.println("SESSION TEST Attribute " + session.getAttribute("x")); /*
																					 * mas há outras variantes, com
																					 * anotações @SessionAttribute
																					 */
		List<String> allNames = (List<String>) session.getAttribute("allNames");
		if (allNames == null) { // se não havia o atributo:
			allNames = new LinkedList<String>();
			session.setAttribute("allNames", allNames); // colocar a lista na sessão, para guardar dados
		}
		// adicionar o nome atual à sessão, na lista
		allNames.add(name);
		// mostrar na view ?
		model.addAttribute("allNames", allNames.toString()); // ajustar a view para mostrar tb esta variável allNames

		return "greeting";
	}

}

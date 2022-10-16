package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Person;
import net.javaguides.springboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {

	@Autowired
	private PersonService personService;

	@RequestMapping("/")
	public String home() {
		return "person.html";
	}

	@RequestMapping("/persons")
	public String showPerson(Model model) {
		model.addAttribute("persons", personService.getAllPersons());
		System.out.println(personService.getAllPersons().get(0).getPersonName());
		return "view.html";
	}

	@RequestMapping("/person")
	public String show( Model model) {
		System.out.println("97654327876543");
		Person person = personService.getPerson(6);
		model.addAttribute("person", person);
		return "view.html";
	}

	@RequestMapping(value = "/person_save", method = RequestMethod.POST)
	public String savePerson(@ModelAttribute Person person, Model model) {
		personService.savePerson(person);
		// model.addAttribute("persons", personService.getAllPersons());
		return "redirect:/person.html";
	}

	@RequestMapping("/person_edit/{persondId}")
	public String editPerson(@PathVariable int persondId, Model model) {
		model.addAttribute("person", personService.getPersonById(persondId));

		System.out.println("person edit :: " + personService.getPersonById(persondId));
		return "redirect:/person.html";
	}

	@GetMapping("/persons/{persondId}")
	public String deleteStudent(@PathVariable int persondId) {
		personService.deletePersonById(persondId);
		return "redirect:/person.html";
	}

}
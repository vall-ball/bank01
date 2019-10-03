package ru.vallball.bank01.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.vallball.bank01.model.Client;
import ru.vallball.bank01.service.ClientService;

@Controller
@RequestMapping(value = {"/clients"})
public class ClientController {
	
	@Autowired
	ClientService clientService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@ModelAttribute("client")
	public Client newClient(@RequestParam(required=false,value = "id") Long id){
		if (id == null) {
		return new Client();
		}
		else return clientService.findClientById(id);
	  }
	
	@GetMapping
	public String clients(@PageableDefault(page = 0, size = 10) Pageable pageable,Model model) {
		Page<Client> page = clientService.findAll(pageable);
		model.addAttribute("page", page);
	    int totalPages =page.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages-1)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
	    return "listClients";
	}
	
	@GetMapping("/addClient")
	public String addClient() {
		return "addClient";
	}
	
	@PostMapping("/addClient")
	public String processRegistration(@ModelAttribute @Valid Client client, BindingResult result) {
		 if(result.hasErrors()) {
			return "addClient";
	        }
		 else {
			 clientService.save(client.toClient(passwordEncoder));
			 return "redirect:/clients";}
	}

	@GetMapping("/deleteClient/{id}")
	public String deleteAccount(@PathVariable Long id, Model model) {
		clientService.delete(id);
		return "redirect:/clients";
	}
	
	@GetMapping("/changeClient/{id}")
	public String changeAccount(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
		Client client = clientService.findClientById(id);
		request.getSession().setAttribute("client", client);
		model.addAttribute("client", client);
		return "changeClient";
	}

	
	@PostMapping("/update")
	public String update(@ModelAttribute @Valid Client client, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "changeClient";
		} else {

			clientService.save(client);

		}
		return "redirect:/clients";
	}
}
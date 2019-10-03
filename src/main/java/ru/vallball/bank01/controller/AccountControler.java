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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vallball.bank01.model.Account;
import ru.vallball.bank01.model.Client;
import ru.vallball.bank01.service.AccountService;
import ru.vallball.bank01.service.ClientService;

@Controller
@RequestMapping(value = {"/accounts"})
public class AccountControler {
	
	@Autowired
	ClientService clientService;
	@Autowired
	AccountService accountService;
	
	@ModelAttribute("account")
	public Account newAccount(@RequestParam(required=false,value = "id") Long id){
		if (id == null) {
		return new Account();
		}
		else return accountService.findAccountById(id);
	  }

	

	@GetMapping
	public String accounts(@PageableDefault(page = 0, size = 10) Pageable pageable,Model model,HttpServletRequest req) {
		Client client = (Client) clientService.loadUserByUsername(req.getRemoteUser());
		model.addAttribute("client", client);
		Page<Account> page = accountService.findByClient(pageable, client);
		model.addAttribute("page", page);
	    int totalPages =page.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages-1)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
	    return "listAccounts";
	}
	
	@GetMapping("/{id}")
	public String accountOfClient(@PathVariable Long id,@PageableDefault(page = 0, size = 10) Pageable pageable,Model model) {
		Client client = clientService.findClientById(id);
		model.addAttribute("client", client);
		Page<Account> page = accountService.findByClient(pageable, client);
		model.addAttribute("page", page);
	    int totalPages =page.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages-1)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
	    return "listAccountsForAdmins";
	}
	
	@GetMapping("/addAccount/{id}")
	public String addAccount(@PathVariable Long id, Model model) {
		Client client = clientService.findClientById(id);
		model.addAttribute("client", client);
		return "addAccount";
	}
	
	@PostMapping("/addAccount/{id}")
	public String addNewAccount(@ModelAttribute("account") @Valid Account account,BindingResult result,@PathVariable Long id, Model model) {
		Client client = clientService.findClientById(id);
		if(result.hasErrors()) {
			 model.addAttribute("client", client);
			 return "addAccount";
	        }
		 else {
			
			 account.setClient(client);
			 accountService.save(account);
	return "redirect:/accounts/{id}";
	}
}
	
	@GetMapping("/deleteAccount/{accId}")
	public String deleteAccount(@PathVariable Long accId, Model model) {
		Long id = accountService.findAccountById(accId).getClient().getId();
		model.addAttribute("id", id);
		accountService.delete(accId);
		return "redirect:/accounts/{id}";
	}
	
	@GetMapping("/changeAccount/{id}")
	public String changeAccount(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
		Account account = accountService.findAccountById(id);
		request.getSession().setAttribute("account", account);
		model.addAttribute("account", account);
		return "changeAccount";
	}
	
	@PostMapping("/update") 
	public String update(@ModelAttribute("account") @Valid Account account, BindingResult result,Model model) {
		Client client = clientService.findClientById(account.getClient().getId());
		if(result.hasErrors()) {
			model.addAttribute("client", client);
			model.addAttribute("account", account);
			return "changeAccount";
	        }
		 else {
			accountService.save(account);
			model.addAttribute("client", client);
			return "redirect:/accounts/" + account.getClient().getId();
	}
	}

	
}

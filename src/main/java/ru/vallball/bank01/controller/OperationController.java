package ru.vallball.bank01.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.vallball.bank01.model.Account;
import ru.vallball.bank01.model.Client;
import ru.vallball.bank01.model.Operation;
import ru.vallball.bank01.service.AccountService;
import ru.vallball.bank01.service.ClientService;
import ru.vallball.bank01.service.OperationService;

@Controller
@RequestMapping(value = {"/operations"})
public class OperationController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	OperationService operationService;
	
	@Autowired
	ClientService clientService;
	
	@ModelAttribute("operations")
	public Operation newOperation(){
		return new Operation();
		  }
	
	@GetMapping("/newOperation/{id}")
	public String newOperation(@PathVariable Long id, Model model) {
		Account account = accountService.findAccountById(id);
		model.addAttribute("account", account);
		return "addOperation";
	}
	
	@PostMapping("/addOperation/{id}")
	public String addOperation(@ModelAttribute("operation") @Valid Operation operation,BindingResult result,
			@PathVariable Long id, @RequestParam("destination_id") Long destination_id,Model model) {
		Account source = accountService.findAccountById(id);
		operation.setSource(source);
		
		if(source.getSum().compareTo(operation.getSum()) < 0) {
			FieldError error = new FieldError("operation", "sum", "У вас нет на счету таких средств!");
			result.addError(error);
		}
		if (accountService.findAccountById(destination_id) == null) {
			FieldError error = new FieldError("operation", "destination", "Такого счёта не существует!");
			result.addError(error);
					}
				if(result.hasErrors()) {
					model.addAttribute(source);
		     return "addOperation";
       }
		else {
			Account destination = accountService.findAccountById(destination_id);
			operation.setDestination(destination);
			operation.setDate(LocalDate.now());
			operation.setTime(LocalTime.now());
			operationService.save(operation);
			model.addAttribute("operation", operation);
			return "operation";
		}
	}
	
	@GetMapping("/listOperations/{id}")
	public String operations(@PageableDefault(page = 0, size = 10) Pageable pageable,Model model,@PathVariable Long id) {
		Client client = clientService.findClientById(id);
		model.addAttribute("client", client);
		PagedListHolder<Operation> page = operationService.findByClient(pageable,client);
		model.addAttribute("page", page);
	    int totalPages =page.getPageCount();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages-1)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
	    return "listOperations";
	}
	
}

package com.azelewski.exercise.controller;

import com.azelewski.exercise.model.Computer;
import com.azelewski.exercise.service.ComputerService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("main")
@AllArgsConstructor
public class ComputerController {
    private final ComputerService computerService;
    private static final Logger LOGGER = LogManager.getLogger(ComputerController.class);

    @GetMapping("")
    public String viewHomePage(Model model) {
        return viewComputers(1, "name", "asc", "", model);
    }

    @GetMapping("{pageNumber}")
    public String viewComputers(@PathVariable(value = "pageNumber") int pageNumber,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDirection") String sortDirection,
                                @RequestParam("searchValue") String searchValue,
                                Model model){
        int pageSize = 10;
        Page<Computer> page = computerService.searchComputers(searchValue,pageNumber,pageSize,sortField,sortDirection);
        List<Computer> computerList = page.getContent();
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection",
                sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("computers", computerList);
        return "main";
    }
    @GetMapping("addComputer")
    public String addComputerForm(Model model){
        Computer computer = new Computer();
        model.addAttribute("computer", computer);
        return "add-computer-form";
    }
    @PostMapping("save")
    public String saveComputer(@ModelAttribute("computer") Computer computer){
        try{
            BigDecimal exchangeRate = computerService.getCurrency(computer.getDate());
            computer.setPricePLN(computer.getPriceUSD().multiply(exchangeRate));
            computerService.save(computer);
            LOGGER.info("Operation successful, computer " + computer.getName() + " added to database.");
            return "show-added-computer";
        }catch (JSONException e){
            LOGGER.error(e);
            LOGGER.error("Operation failed, computer " + computer.getName() + "was NOT added to database.");
            return "operation-failed";
        }catch (ResourceAccessException e){
            LOGGER.error("Operation failed, connection error");
            LOGGER.error(e);
            return "operation-failed";
        }
    }
    @PostMapping("saveToFile")
    public String saveAsXml(@RequestParam("fileName") String fileName,
                            @RequestParam("sortField") String sortField,
                            @RequestParam("sortDirection") String sortDirection,
                            @RequestParam("searchValue") String searchValue){
        Page<Computer> page = computerService.searchComputers(searchValue,1,Integer.MAX_VALUE,sortField,sortDirection);
        List<Computer> computers = page.getContent();
        computerService.saveToFile(computers, fileName);
        return "redirect:/main/1?searchValue=" + searchValue + "&sortField=" + sortField + "&sortDirection=" + sortDirection;
    }
}

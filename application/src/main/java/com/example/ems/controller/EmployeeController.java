package com.example.ems.controller;

import com.example.ems.model.Employee;
import com.example.ems.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("employees", employeeService.search(q));
        model.addAttribute("q", q);
        return "employees";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        Employee e = new Employee();
        e.setJoiningDate(LocalDate.now());
        model.addAttribute("employee", e);
        return "add-employee";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("employee") @Valid Employee employee, BindingResult br) {
        if (br.hasErrors()) {
            return "add-employee";
        }
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        employeeService.findById(id).ifPresent(e -> model.addAttribute("employee", e));
        return "employee-details";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/employees";
    }
}

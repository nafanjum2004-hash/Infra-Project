package com.example.ems.service;

import com.example.ems.model.Employee;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final List<Employee> employees = new CopyOnWriteArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @PostConstruct
    public void init() {
        // Seed with sample data
        save(new Employee(null, "Alice", "Smith", "alice.smith@example.com", "Engineering", "Developer", LocalDate.now().minusYears(1)));
        save(new Employee(null, "Bob", "Johnson", "bob.johnson@example.com", "HR", "Manager", LocalDate.now().minusMonths(6)));
        save(new Employee(null, "Carol", "Davis", "carol.davis@example.com", "Marketing", "Specialist", LocalDate.now().minusDays(30)));
    }

    public List<Employee> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(employees));
    }

    public Optional<Employee> findById(Long id) {
        return employees.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(idCounter.getAndIncrement());
            employees.add(employee);
        } else {
            // update
            delete(employee.getId());
            employees.add(employee);
        }
        return employee;
    }

    public boolean delete(Long id) {
        return employees.removeIf(e -> e.getId().equals(id));
    }

    public long count() {
        return employees.size();
    }

    public List<Employee> search(String query) {
        if (query == null || query.trim().isEmpty()) return findAll();
        String q = query.toLowerCase().trim();
        return employees.stream()
                .filter(e -> (e.getFirstName() + " " + e.getLastName()).toLowerCase().contains(q)
                        || e.getEmail().toLowerCase().contains(q)
                        || e.getDepartment().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<Employee> recent(int n) {
        List<Employee> copy = new ArrayList<>(employees);
        copy.sort((a, b) -> b.getJoiningDate().compareTo(a.getJoiningDate()));
        return copy.stream().limit(n).collect(Collectors.toList());
    }
}

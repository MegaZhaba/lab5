package com.example.Lab5.controllers;

import com.example.Lab5.entities.ApplicationRequest;
import com.example.Lab5.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("requests", requestService.getAllRequests());
        return "list";
    }

    @GetMapping("/new")
    public String addForm(Model model) {
        model.addAttribute("request", new ApplicationRequest());
        model.addAttribute("courses", requestService.getAllCourses());
        model.addAttribute("operators", requestService.getAllOperators());
        return "add";
    }

    @PostMapping("/new")
    public String createRequest(@RequestParam String userName,
                                @RequestParam String commentary,
                                @RequestParam String phone,
                                @RequestParam Long courseId,
                                @RequestParam(required = false) List<Long> operatorIds) {
        requestService.createRequest(userName, commentary, phone, courseId, operatorIds);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        ApplicationRequest req = requestService.getRequestById(id);
        if (req == null) return "redirect:/";
        model.addAttribute("request", req);
        return "details";
    }

    @PostMapping("/{id}/process")
    public String processRequest(@PathVariable Long id) {
        requestService.markAsProcessed(id);
        return "redirect:/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        requestService.deleteRequest(id);
        return "redirect:/";
    }
    @GetMapping("/pending")
    public String pending(Model model) {
        model.addAttribute("requests", requestService.getPendingRequests());
        return "pending";
    }
    @GetMapping("/processed")
    public String processed(Model model) {
        model.addAttribute("requests", requestService.getProcessedRequests());
        return "processed";
    }
}
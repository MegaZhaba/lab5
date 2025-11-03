package com.example.Lab5.controllers;

import com.example.Lab5.entities.ApplicationRequest;
import com.example.Lab5.services.RequestService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        List<ApplicationRequest> requests = requestService.getAllRequests();

        if (requests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(requests, HttpStatus.OK);
        }
    }

//    @GetMapping("/new")
//    public String addForm(Model model) {
//        model.addAttribute("request", new ApplicationRequest());
//        model.addAttribute("courses", requestService.getAllCourses());
//        model.addAttribute("operators", requestService.getAllOperators());
//        return "add";
//    }

    @PostMapping("/new")
    public ResponseEntity<ApplicationRequest> createRequest(@RequestBody ApplicationRequest applicationRequest) {
        ApplicationRequest created = requestService.createRequest(applicationRequest);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable(value = "id") Long id) {
        ApplicationRequest req = requestService.getRequestById(id);
        if (req == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(req, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}/process")
    public ResponseEntity<String> processRequest(@PathVariable Long id) {
        boolean updated = requestService.markAsProcessed(id);

        if (updated) {
            return ResponseEntity.ok("Request with ID " + id + " has been processed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Request with ID " + id + " not found.");
        }
    }


    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id ) {
        boolean deleteReq = requestService.deleteRequest(id);

        if (deleteReq) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/pending")
    public ResponseEntity<?> pending() {
        List<ApplicationRequest> req = requestService.getPendingRequests();
        if (req == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(req, HttpStatus.OK);
        }
    }
    @GetMapping("/processed")
    public ResponseEntity<?> processed() {
        List<ApplicationRequest> req = requestService.getProcessedRequests();
        if (req == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(req, HttpStatus.OK);
        }
    }
}
package com.example.Lab5.services;

import com.example.Lab5.entities.ApplicationRequest;
import com.example.Lab5.entities.Courses;
import com.example.Lab5.entities.Operators;
import com.example.Lab5.repository.ApplicationRequestRepository;
import com.example.Lab5.repository.CourseRepository;
import com.example.Lab5.repository.OperatorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private ApplicationRequestRepository applicationRequestRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private OperatorsRepository operatorRepository;

    public List<ApplicationRequest> getAllRequests() {
        return applicationRequestRepository.findAll();
    }

    public ApplicationRequest getRequestById(Long id) {
        return applicationRequestRepository.findById(id).orElse(null);
    }

    public boolean deleteRequest(Long id) {
        applicationRequestRepository.deleteById(id);
        return true;
    }

    public List<ApplicationRequest> getPendingRequests() {
        return applicationRequestRepository.findByHandled(false);
    }

    public List<ApplicationRequest> getProcessedRequests() {
        return applicationRequestRepository.findByHandled(true);
    }

    public List<Courses> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Operators> getAllOperators() {
        return operatorRepository.findAll();
    }

    public ApplicationRequest createRequest(ApplicationRequest request) {
        Courses course = courseRepository.findById(request.getCourseId()).orElse(null);
        if (course == null) return null;

        ApplicationRequest req = new ApplicationRequest();
        req.setUserName(request.getUserName());
        req.setCommentary(request.getCommentary());
        req.setPhone(request.getPhone());
        req.setCourse(course);

        if (request.getOperatorIds() != null && !request.getOperatorIds().isEmpty()) {
            List<Operators> operators = operatorRepository.findAllById(request.getOperatorIds());
            req.setOperators(operators);
        }
        req.setHandled(false);
        return applicationRequestRepository.save(req);
    }

    public boolean markAsProcessed(Long id) {
        ApplicationRequest req = applicationRequestRepository.findById(id).orElse(null);

        if (req == null) {
            return false;
        }

        if (!req.isHandled()) {
            req.setHandled(true);
            applicationRequestRepository.save(req);
        }

        return true; // всё прошло успешно
    }

}

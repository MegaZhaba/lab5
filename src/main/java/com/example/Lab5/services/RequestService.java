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

    public void deleteRequest(Long id) {
        applicationRequestRepository.deleteById(id);
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

    public void createRequest(String userName, String commentary, String phone,
                              Long courseId, List<Long> operatorIds) {
        Courses course = courseRepository.findById(courseId).orElse(null);
        if (course == null) return;

        ApplicationRequest req = new ApplicationRequest();
        req.setUserName(userName);
        req.setCommentary(commentary);
        req.setPhone(phone);
        req.setCourse(course);

        if (operatorIds != null && !operatorIds.isEmpty()) {
            List<Operators> operators = operatorRepository.findAllById(operatorIds);
            req.setOperators(operators);
        }
        req.setHandled(false);
        applicationRequestRepository.save(req);
    }
    public void markAsProcessed(Long id) {
        ApplicationRequest req = applicationRequestRepository.findById(id).orElse(null);
        if (req != null && !req.isHandled()) {
            req.setHandled(true);
            applicationRequestRepository.save(req);
        }
    }
}

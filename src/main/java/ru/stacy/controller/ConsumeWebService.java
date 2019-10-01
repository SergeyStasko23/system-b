package ru.stacy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.stacy.domain.Student;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ConsumeWebService {
    private final RestTemplate restTemplate;

    private static final String RESOURCE_PATH = "http://localhost:8090/api/students";

    @RequestMapping(value = "/api/template/students")
    public String getAllStudents() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(RESOURCE_PATH, HttpMethod.GET, entity, String.class).getBody();
    }

    @RequestMapping(value = "/api/template/get-for-all-entity/students")
    public List<Student> getForAllEntity() {
        Map<String, String> urlParameters = new HashMap<>();
        ResponseEntity<Student[]> entity = restTemplate.getForEntity(RESOURCE_PATH, Student[].class, urlParameters);

        return entity.getBody() != null ? Arrays.asList(entity.getBody()) : Collections.emptyList();
    }

    @RequestMapping(value = "/api/template/get-for-all-object/students")
    public Student[] getForAllObject() {
        return restTemplate.getForObject(RESOURCE_PATH, Student[].class);
    }

    @RequestMapping(value = "/api/template/students/{studentId}")
    public String getStudentById(@PathVariable("studentId") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(RESOURCE_PATH + "/" + id, HttpMethod.GET, entity, String.class).getBody();
    }

    @RequestMapping(value = "/api/template/get-for-entity/students/{studentId}")
    public ResponseEntity<Student> getForEntity(@PathVariable("studentId") Long id) {
        ResponseEntity<Student> entity = restTemplate.getForEntity(RESOURCE_PATH + "/" + id, Student.class, Long.toString(id));

        log.info("Status code value: " + entity.getStatusCodeValue());
        log.info("HTTP Header 'ContentType': " + entity.getHeaders().getContentType());

        return entity;
    }

    @RequestMapping(value = "/api/template/get-for-object/students/{studentId}")
    public Student getForObject(@PathVariable("studentId") Long id) {
        return restTemplate.getForObject(RESOURCE_PATH + "/" + id, Student.class, Long.toString(id));
    }
}

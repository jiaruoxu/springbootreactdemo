package com.example.demo.student;

import com.example.demo.exception.ApiRequestExceprion;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public List<Student> getAllStudents() {
//        throw new ApiRequestExceprion("oops cannot get student");
//        throw new IllegalStateException("oops cannot get student");
        return studentService.getAllStudents();
    }

    @GetMapping(path = "{studentId}")
    public List<StudentCourse> getAllCourseForStudents(@PathVariable("studentId") UUID studentId) {
        return studentService.getAllCourseForStudents(studentId);
    }

    @PostMapping
    public void addNewStudent(@RequestBody @Valid Student student){
        studentService.addNewStudent(student);
    }
}

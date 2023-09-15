package com.example.demo.student;

import com.example.demo.EmailValidator;
import com.example.demo.exception.ApiRequestExceprion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentDataAccessService studentDataAccessService;
    private final EmailValidator emailValidator;

    @Autowired
    public StudentService(StudentDataAccessService studentDataAccessService, EmailValidator emailValidator) {
        this.studentDataAccessService = studentDataAccessService;
        this.emailValidator = emailValidator;
    }

    List<Student> getAllStudents(){
        return studentDataAccessService.getAllStudents();
    }
    void addNewStudent(Student student){
        addNewStudent(null, student);
    }
    void addNewStudent(UUID studentId, Student student){
        UUID newStudentId = Optional.ofNullable(studentId).orElse(UUID.randomUUID());

        if(!emailValidator.test(student.getEmail()))
            throw new ApiRequestExceprion(student.getEmail()+" is not valid");

        if(studentDataAccessService.isEmailTaken(student.getEmail()))
            throw new ApiRequestExceprion(student.getEmail()+" is taken");
        studentDataAccessService.insertStudent(newStudentId, student);
    }

    List<StudentCourse> getAllCourseForStudents(UUID studentId) {
        return studentDataAccessService.getAllCourseForStudents(studentId);
    }
}

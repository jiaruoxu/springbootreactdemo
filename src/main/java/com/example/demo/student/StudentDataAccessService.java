package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class StudentDataAccessService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Student> getAllStudents(){
        String sql=""+
                "select student_id,"+
                "first_name,"+
                "last_name,"+
                "email,"+
                "gender"+
                " from student;";
        return  jdbcTemplate.query(sql, getStudentFromDb());
//        return List.of(
//                new Student(UUID.randomUUID(),"James","Bond","jamesbond@gmai.com", Student.Gender.MALE),
//                new Student(UUID.randomUUID(),"Elisa","tamera","Elisatamera@gmai.com", Student.Gender.FEMALE)
//        );
    }
    int insertStudent(UUID newStudentId, Student student) {
        String sql = ""+
                "insert into student (student_id, first_name, last_name, email, gender) values(?,?,?,?,?::gender)";
        return jdbcTemplate.update(sql,
                newStudentId,
                student.getFirstName(),
                student.getlastName(),
                student.getEmail(),
                student.getGender().name().toUpperCase());
//                student.getGender());

    }
    private static RowMapper<Student> getStudentFromDb() {
        return (resultSet, i) -> {
            String studentIdStr = resultSet.getString("student_id");
            UUID studentId = UUID.fromString(studentIdStr);
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String email = resultSet.getString("email");
            String genderStr = resultSet.getString("gender");
            Student.Gender gender = Student.Gender.valueOf(genderStr);
            return new Student(studentId, firstName, lastName, email, gender);
        };
    }


    boolean isEmailTaken(String email) {
        String sql = ""+
                "select exists( select 1 from student where email =?)";
        return jdbcTemplate.queryForObject(sql,
                new Object[] {email},
                (resultSet,i)-> resultSet.getBoolean(1)
        );


    }

    private static RowMapper<StudentCourse> getStudentCourseFromDb() {
        return (resultSet, i) -> {
            return new StudentCourse(
                    UUID.fromString(resultSet.getString("student_id")),
                    UUID.fromString(resultSet.getString("course_id")),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("department"),
                    resultSet.getString("teacher_name"),
                    resultSet.getDate("start_date").toLocalDate(),
                    resultSet.getDate("end_date").toLocalDate(),
                    Optional.ofNullable(resultSet.getString("grade"))
                            .map(Integer::parseInt)
                            .orElse(null)
            );
        };
    }

    List<StudentCourse> getAllCourseForStudents(UUID studentId) {
        String sql=""+
                "select student.student_id," +
                "course.course_id," +
                "course.name," +
                "course.description,"+
                "course.department,"+
                "course.teacher_name,"+
                "course.description,"+
                "student_course.start_date,"+
                "student_course.end_date,"+
                "student_course.grade "+
                "from student " +
                "join student_course using (student_id) "+
                "join course         using (course_id) " +
                "where student.student_id=? ;";
        return  jdbcTemplate.query(sql,
                new Object[]{studentId} ,
                getStudentCourseFromDb());

    }
}

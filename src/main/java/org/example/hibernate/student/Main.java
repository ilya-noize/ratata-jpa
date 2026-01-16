package org.example.hibernate.student;

import org.example.hibernate.student.service.StudentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.example.hibernate.student");
        StudentService studentService = context.getBean(StudentService.class);

        Student studentFirst = new Student("Mark", 22);
        Student studentSecond = new Student("Will", 20);

        studentService.saveStudent(studentFirst);
        studentService.saveStudent(studentSecond);
    }
}
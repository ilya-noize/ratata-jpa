package org.example.hibernate.student;

import org.example.hibernate.student.service.ProfileService;
import org.example.hibernate.student.service.StudentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.example.hibernate.student");

        StudentService studentService = context.getBean(StudentService.class);
        ProfileService profileService = context.getBean(ProfileService.class);

        Student studentFirst = new Student("Mark", 22);
        Student studentSecond = new Student("Will", 20);

        studentService.saveStudent(studentFirst);
        studentService.saveStudent(studentSecond);

        Profile profileFirst = new Profile("My Bio", LocalDateTime.now(), studentFirst);
        profileService.saveProfile(profileFirst);
/*
        session = sessionFactory.openSession();

        session.beginTransaction();
        Student student = session.find(Student.class, 1L);
        session.getTransaction().commit();

        session.beginTransaction();
        Student student2 = session.find(Student.class, 1L);
//        session.remove(profileFirst);
        session.remove(student2);
        session.getTransaction().commit();


        session.close();
        */
    }
}
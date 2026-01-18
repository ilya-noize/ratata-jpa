package org.example.hibernate.student;

import org.example.hibernate.student.model.Group;
import org.example.hibernate.student.model.Student;
import org.example.hibernate.student.service.GroupService;
import org.example.hibernate.student.service.ProfileService;
import org.example.hibernate.student.service.StudentService;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.example.hibernate.student");

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);
        StudentService studentService = context.getBean(StudentService.class);
        ProfileService profileService = context.getBean(ProfileService.class);
        GroupService groupService = context.getBean(GroupService.class);

        Group groupFirst = groupService.saveGroup("1", 2024L);
        Group groupSecond = groupService.saveGroup("2", 2024L);
        Group groupThird = groupService.saveGroup("3", 2024L);

        Student studentFirst = new Student("Mark", 22, groupFirst);
        Student studentSecond = new Student("Will", 20, groupFirst);

        studentService.saveStudent(studentFirst);
        studentService.saveStudent(studentSecond);

        System.out.println("-".repeat(40));
        groupService.findAll().forEach(System.out::println);
    }
}
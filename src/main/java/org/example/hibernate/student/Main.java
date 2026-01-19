package org.example.hibernate.student;

import org.example.hibernate.student.model.Course;
import org.example.hibernate.student.model.Group;
import org.example.hibernate.student.model.Student;
import org.example.hibernate.student.service.CourseService;
import org.example.hibernate.student.service.GroupService;
import org.example.hibernate.student.service.ProfileService;
import org.example.hibernate.student.service.StudentService;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.example.hibernate.student");

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);
        StudentService studentService = context.getBean(StudentService.class);
        ProfileService profileService = context.getBean(ProfileService.class);
        GroupService groupService = context.getBean(GroupService.class);
        CourseService courseService = context.getBean(CourseService.class);

        List<Group> groups = new ArrayList<>();
        groups.add(groupService.saveGroup("1", 2024L));
        groups.add(groupService.saveGroup("2", 2024L));

        List<Student> students = new ArrayList<>();
        students.add(new Student("Mark", 22, groups.getFirst()));
        students.add(new Student("Will", 20, groups.getFirst()));
        students.forEach(studentService::saveStudent);

        List<Course> courses = new ArrayList<>();
        courses.add(new Course("math-1", "math"));
        courses.add(new Course("math-2", "math"));
        courses.add(new Course("math-3", "math"));
        courses.forEach(courseService::saveCourse);

        System.out.println("-".repeat(40));
//        groupService.findAll().forEach(System.out::println);

        System.out.println("-".repeat(40));
//        studentService.findAll().forEach(System.out::println);

        System.out.println("-".repeat(40));
        courseService.enrollStudentToCourse(courses.getFirst().id(), students.getFirst().id());
        courseService.enrollStudentToCourse(courses.getLast().id(), students.getFirst().id());
        courseService.enrollStudentToCourse(courses.getLast().id(), students.getLast().id());

        Student studentById1 = studentService.getById(students.getFirst().id());
        System.out.println("studentById1 = " + studentById1);
//        profileService.findAll().forEach(System.out::println);

        System.out.println("-".repeat(40));
//        courseService.findAll().forEach(System.out::println);
    }
}
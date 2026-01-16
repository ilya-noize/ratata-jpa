package org.example.hibernate.student.service;

import org.example.hibernate.student.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final SessionFactory sessionFactory;

    public StudentService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Student saveStudent(Student student) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.persist(student);
        session.getTransaction().commit();

        session.close();
        return student;
    }

    public void deleteStudent(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Student studentForDelete = session.find(Student.class, id);
        session.remove(studentForDelete);
        session.getTransaction().commit();
        session.close();
    }

    public Student getById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Student student = session.find(Student.class, id);
        session.close();
        return student;
    }

    public List<Student> findAll() {
        Session session = sessionFactory.openSession();
        List<Student> students = session
                .createQuery("SELECT s FROM Student s", Student.class)
                .list();
        session.close();
        return students;
    }

    public Student updateStudent(Student student) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        student = session.merge(student);

        session.getTransaction().commit();
        session.close();
        return student;
    }
}

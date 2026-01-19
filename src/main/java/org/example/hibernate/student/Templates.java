package org.example.hibernate.student;

import jakarta.persistence.NoResultException;
import org.example.hibernate.student.model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.List;

public class Templates {
    private Session session;

    private void selectById(Long id) {
        try {
            Student studentById2 = session
                    .createQuery("SELECT s FROM Student s where s.id = :id", Student.class)
                    .setParameter("id", id)
                    .getSingleResult();
            System.out.printf("student ID:%s = %s%n", id, studentById2);
        } catch (NoResultException error) {
            System.out.println("error.getMessage() = " + error.getMessage());
        }
    }

    void aVoid(Session session) {
        saveStudent(session, new Student("Will", 22, null));
        saveStudent(session, new Student("John", 20, null));

        Student studentById1 = session.find(Student.class, 1L);
        System.out.println("studentById1 = " + studentById1);

        selectById(1L);


        Transaction transaction = session.beginTransaction();
        Student studentForUpdate = session.find(Student.class, 1L);
        studentForUpdate.setAge(25);
        studentForUpdate.setName("Mark");
        transaction.commit();

        getList(session);

        Student studentByName = session
                .createQuery("SELECT s FROM Student s where s.name = :name", Student.class)
                .setParameter("name", "Mark")
                .getSingleResult();
        System.out.println("Student by name = " + studentByName);

        session.close();
    }

    private void saveStudent(Session session, Student student) {
        Transaction transaction = session.beginTransaction();
        session.persist(student);
        TransactionStatus status = transaction.getStatus();
        System.out.println("status = " + status);
        transaction.commit();
        selectById(1L);
    }

    private void getList(Session session) {
        List<Student> students = session
                .createQuery("SELECT s FROM Student s", Student.class)
                .list();

        for (Student student : students) {
            System.out.println("student = " + student);
        }
    }

    private void deleteById2NativeQuery(Session session) {
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery("delete from students s where s.id = 2")
                .executeUpdate();
        transaction.commit();
    }

    private void deleteById2_ver2(Session session) {
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM Student s WHERE s.id = 2")
                .executeUpdate();
        transaction.commit();
    }

    private void deleteById2(Session session) {

    }
}

package org.example.hibernate.student.service;

import org.example.hibernate.student.model.Course;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final TransactionHelper transactionHelper;

    public CourseService(TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public Course saveCourse(Course course) {
        return transactionHelper.executeInTransaction(session -> {
            session.persist(course);
            return course;
        });
    }

    public void enrollStudentToCourse(
            Long courseId,
            Long studentId
    ) {
        transactionHelper.executeInTransaction(session -> {
//            Student student = session.find(Student.class, studentId);
//            Course course = session.find(Course.class, courseId);
//            student.courseList().add(course);
            String query = """
                    INSERT INTO student_courses (student_id, course_id)
                    VALUES (:studentId, :courseId);
                    """;
            session.createNativeQuery(query, Void.class)
                    .setParameter("studentId", studentId)
                    .setParameter("courseId", courseId)
                    .executeUpdate();
        });
    }
}

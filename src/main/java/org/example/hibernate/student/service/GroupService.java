package org.example.hibernate.student.service;

import org.example.hibernate.student.model.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;

    public GroupService(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public Group saveGroup(
            String number,
            Long gradYear
    ) {
        return transactionHelper.executeInTransaction(session -> {
            Group group = new Group(number, gradYear);
            session.persist(group);
            return group;
        });
    }

    public List<Group> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("""
                    SELECT g from Group g
                    LEFT JOIN FETCH g.studentList s
                    LEFT JOIN FETCH s.profile
                    """, Group.class).list();
        }
    }
}

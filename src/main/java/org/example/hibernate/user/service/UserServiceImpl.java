package org.example.hibernate.user.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.hibernate.user.User;
import org.example.hibernate.user.aop.Loggable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.hibernate.user.UserApp.getEntityState;

@Service
public class UserServiceImpl implements UserService {
    private final SessionFactory sessionFactory;

    public UserServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Loggable
    public User save(User user) {
        try (Session session = sessionFactory.openSession()) {
            // user was TRANSIENT
            System.out.printf("%nSAVE before STATUS = %s%n", getEntityState(user, session));
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            // user will PERSISTENT
            System.out.printf("SAVE after STATUS = %s%n%n", getEntityState(user, session));

        }
        return user;
    }

    @Override
    @Loggable
    public User update(User user) {
        try (Session session = sessionFactory.openSession()) {
            // user was DETACHED (if saved) / TRANSIENT (if not saved)
            System.out.printf("UPDATE before STATUS = %s%n", getEntityState(user, session));
            session.beginTransaction();
            User merged = session.merge(user);
            session.getTransaction().commit();
            // user will DETACHED (if saved) / TRANSIENT (if not saved)
            System.out.printf("UPDATE after STATUS = %s%n%n", getEntityState(user, session));
            return merged;
        }
    }

    @Override
    @Loggable
    public User getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.find(User.class, id);
            // user will PERSISTENT
            System.out.printf("GET after STATUS = %s%n%n", getEntityState(user, session));
            return user;
        }
    }

    @Override
    @Loggable
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);
            return session.createQuery(cq).getResultList();
        }
    }

    @Override
    @Loggable
    public void removeById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.find(User.class, id);
            // user was PERSISTENT
            System.out.printf("DELETE before STATUS = %s%n", getEntityState(user, session));
            session.remove(user);
            session.getTransaction().commit();
            // user will REMOVED
            System.out.printf("DELETE after STATUS = %s%n", getEntityState(user, session));
        }
    }
}

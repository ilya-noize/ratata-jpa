package org.example.hibernate.user.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.hibernate.user.aop.Loggable;
import org.example.hibernate.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.hibernate.user.UserApp.getEntityState;

@Service
public class UserService {
    private final TransactionService transactionService;

    public UserService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Loggable
    public User save(User user) {
        return transactionService.execute(session -> {
            session.persist(user);
            return user;
        });
    }

    @Loggable
    public User update(User user) {
        return transactionService.execute(session -> {
            // user was DETACHED (if saved) / TRANSIENT (if not saved)
            System.out.printf("UPDATE before STATUS = %s%n", getEntityState(user, session));
            User merge = session.merge(user);
            // user will DETACHED (if saved) / TRANSIENT (if not saved)
            System.out.printf("UPDATE after STATUS = %s%n%n", getEntityState(merge, session));
            return merge;
        });
    }

    @Loggable
    public User getById(Long id) {
        return transactionService.execute(session -> {
            User user = session.find(User.class, id);
            System.out.printf("GET after STATUS = %s%n%n", getEntityState(user, session));
            return user;
        });
    }

    @Loggable
    public List<User> getAll() {
        return transactionService.execute(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);
            return session.createQuery(cq).getResultList();
        });
    }

    @Loggable
    public void removeById(Long id) {
        transactionService.execute(session -> {
            User user = session.find(User.class, id);
            // user was PERSISTENT
            System.out.printf("DELETE before STATUS = %s%n", getEntityState(user, session));
            session.remove(user);
            // user will REMOVED
            System.out.printf("DELETE after STATUS = %s%n", getEntityState(user, session));
        });
    }
}

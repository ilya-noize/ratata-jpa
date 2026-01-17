package org.example.hibernate.user.service;

import jakarta.persistence.criteria.CriteriaQuery;
import org.example.hibernate.user.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {
    private final TransactionService transactionService;

    public UserProfileService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public UserProfile save(UserProfile entity) {
        return transactionService.execute(session -> {
            session.persist(entity);
            return entity;
        });
    }

    public UserProfile update(UserProfile entity) {
        return transactionService.execute(session -> {
            return session.merge(entity);
        });
    }

    public UserProfile getById(Long id) {
        return transactionService.execute(session -> {
            return session.find(UserProfile.class, id);
        });
    }

    public List<UserProfile> getAll() {
        return transactionService.execute(session -> {
            CriteriaQuery<UserProfile> query = session
                    .getCriteriaBuilder()
                    .createQuery(UserProfile.class);
            query.select(query.from(UserProfile.class));

            return session.createQuery(query).getResultList();
        });
    }

    public void removeById(Long id) {
        transactionService.execute(session -> {
            UserProfile userProfile = session.find(UserProfile.class, id);
            session.remove(userProfile);
        });
    }
}

package org.example.hibernate.student.service;

import org.example.hibernate.student.model.Profile;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final TransactionHelper transactionHelper;

    public ProfileService(TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public Profile saveProfile(Profile profile) {
        return transactionHelper.executeInTransaction(session -> {
            session.persist(profile);
            return profile;
        });
    }
}

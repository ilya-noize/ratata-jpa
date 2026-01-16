package org.example.hibernate.user.service;

import org.example.hibernate.user.User;

import java.util.List;

public interface UserService {
    User save(User user);

    User update(User user);

    User getById(Long id);

    List<User> getAll();

    void removeById(Long id);
}

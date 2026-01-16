package org.example.hibernate.user;

import org.example.hibernate.user.service.UserService;
import org.hibernate.Session;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Serializable;

public class UserApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.example.hibernate.user");
        UserService userService = context.getBean(UserService.class);

        User admin = new User("admin", "admin@telecom123.ru");
        userService.save(admin);

        admin.setEmail("root14@telecom123.ru");
        userService.update(admin);

        User consumer = new User("consumer", "consumer@telecom123.ru");
        User provider = new User("provider", "provider@telecom123.ru");
        User infouser = new User("info", "info@telecom123.ru");
        userService.save(consumer);
        userService.save(provider);
        userService.update(infouser);



        userService.getById(consumer.id());
        userService.getById(admin.id());

        userService.getAll().forEach(System.out::println);

        userService.removeById(provider.id());

        context.close();
    }

    public static EntityState getEntityState(User entity, Session session) {
        if (entity == null) return null;

        Serializable id = entity.id(); // замените на getter ID

        if (id == null) return EntityState.TRANSIENT;

        if (!session.contains(entity)) {
            // Проверяем, существует ли запись в БД
            try {
                Object found = session.find(entity.getClass(), id);
                return (found == null) ? EntityState.REMOVED : EntityState.DETACHED;
            } catch (Exception e) {
                return EntityState.REMOVED;
            }
        }

        return EntityState.PERSISTENT;
    }

    public enum EntityState {
        TRANSIENT, PERSISTENT, DETACHED, REMOVED
    }
}

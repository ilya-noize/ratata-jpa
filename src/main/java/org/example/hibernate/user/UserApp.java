package org.example.hibernate.user;

import org.example.hibernate.user.model.User;
import org.example.hibernate.user.model.UserProfile;
import org.example.hibernate.user.service.UserProfileService;
import org.example.hibernate.user.service.UserService;
import org.hibernate.Session;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Serializable;

public class UserApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.example.hibernate.user");

        UserService userService = context.getBean(UserService.class);
        UserProfileService userProfileService = context.getBean(UserProfileService.class);

        User admin = new User("admin", "admin@telecom123.ru");
        User save1 = userService.save(admin);
        System.out.println("save1 = " + save1);

        User consumer = new User("consumer", "consumer@telecom123.ru");
        User save2 = userService.save(consumer);
        System.out.println("save2 = " + save2);

        User byId1 = userService.getById(admin.id());
        System.out.println("byId1 = " + byId1);
        User byId2 = userService.getById(consumer.id());
        System.out.println("byId2 = " + byId2);

        UserProfile adminProfile = new UserProfile("summary One", admin);
        UserProfile userProfile1 = userProfileService.save(adminProfile);
        System.out.println("userProfile1 = " + userProfile1);


        UserProfile consumerProfile = new UserProfile("summary Consumer", consumer);
        UserProfile userProfile2 = userProfileService.save(consumerProfile);
        System.out.println("userProfile2 = " + userProfile2);

        userService.getAll().forEach(System.out::println);
        userService.removeById(consumer.id());

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

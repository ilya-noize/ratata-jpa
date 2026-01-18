package org.example.hibernate.student;

import org.example.hibernate.student.model.Group;
import org.example.hibernate.student.model.Profile;
import org.example.hibernate.student.model.Student;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HibernateConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        Configuration configuration = new Configuration();

        configuration
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Profile.class)
                .addAnnotatedClass(Group.class)
                .addPackage("org.example.hibernate.student")
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres")
                .setProperty("hibernate.connection.username", "postgres")
                .setProperty("hibernate.connection.password", "root")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create-drop");

        return configuration.buildSessionFactory();
    }
}

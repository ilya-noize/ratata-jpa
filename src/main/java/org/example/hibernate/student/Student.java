package org.example.hibernate.student;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.StringJoiner;

/**
 * HibernateConfiguration add:
 * .addAnnotatedClass(Student.class)
 */

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fio", unique = true, nullable = false)
    private String name;

    @Column(name = "vozrast")
    private Integer age;

    public Student() {
    }

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Long id() {
        return id;
    }

    public Student setId(Long id) {
        this.id = id;
        return this;
    }

    public String name() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public Integer age() {
        return age;
    }

    public Student setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Student.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("age=" + age)
                .toString();
    }
}

package org.example.hibernate.movie;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {
    private static Session session;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.example.hibernate.movie");
        SessionFactory sessionFactory = context.getBean(SessionFactory.class);
        session = sessionFactory.openSession();

        Movie movie1 = addMovie(new Movie("FIlm 1", "action", 2003));
        Movie movie2 = addMovie(new Movie("Film 2", "drama", 2010));
        addMovie(new Movie("Film 3", "triller", 1987));
        addMovie(new Movie("Film 4", "triller", 1988));
        addMovie(new Movie("Film 5", "triller", 1989));

        List<Movie> movies = getAllMovies();
        System.out.println("movies = " + movies.size());

        List<Movie> findActions = findByGenre("triller");
        findActions.forEach(System.out::println);

        updateTitle(movie1.getId(), "Film1");
        selectById(movie1.getId());

        deleteMovie(movie2.getId());

        getAllMovies();

        session.close();
    }

    private static Movie addMovie(Movie movie) {
        Transaction transaction = session.beginTransaction();
        session.persist(movie);
        transaction.commit();
        System.out.println("SAVE movie = " + movie);
        return movie;
    }

    private static List<Movie> getAllMovies() {
        String jpql = "SELECT m FROM Movie m";
        List<Movie> movies = session
                .createQuery(jpql, Movie.class)
                .list();

        return movies.isEmpty() ? List.of() : movies;
    }

    private static List<Movie> findByGenre(String genre) {
        String jpql = "SELECT m FROM Movie m WHERE m.genre = :genre";
        List<Movie> moviesWithGenre = session
                .createQuery(jpql, Movie.class)
                .setParameter("genre", genre)
                .getResultList();

        return moviesWithGenre.isEmpty() ? List.of() : moviesWithGenre;
    }

    private static void updateTitle(Long id, String title) {
        Transaction transaction = session.beginTransaction();
        String jpql = "UPDATE Movie m SET m.title = :title WHERE m.id = :id";
        Query query = session.createQuery(jpql);
        query.setParameter("title", title)
                .setParameter("id", id).executeUpdate();
        transaction.commit();
    }

    private static void deleteMovie(Long id) {
        Transaction transaction = session.beginTransaction();
        String jpql = "DELETE FROM Movie m WHERE m.id = :id";
        Query query = session.createQuery(jpql);
        query.setParameter("id", id).executeUpdate();
        transaction.commit();
    }

    private static void selectById(Long id) {
        try {
            String jpql = "SELECT s FROM Movie s where s.id = :id";
            Query<Movie> query = session.createQuery(jpql, Movie.class);
            Movie movie = query.setParameter("id", id).getSingleResult();

            System.out.printf("movie ID:%s = %s%n", id, movie);
        } catch (NoResultException error) {
            System.out.println("error.getMessage() = " + error.getMessage());
        }
    }
}
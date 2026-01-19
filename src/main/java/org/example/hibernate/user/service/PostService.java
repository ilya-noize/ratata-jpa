package org.example.hibernate.user.service;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Root;
import org.example.hibernate.user.model.Post;
import org.example.hibernate.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final TransactionService transactionService;

    public PostService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Post createPost(Post post) {
        return transactionService.execute(session -> {
            session.persist(post);
            return post;
        });
    }

    public List<Post> getAll() {
        return transactionService.execute(session -> {
            CriteriaQuery<Post> query = session
                    .getCriteriaBuilder()
                    .createQuery(Post.class);

            Root<Post> post = query.from(Post.class);
            Fetch<Post, User> user = post.fetch("user");
            user.fetch("userProfile");

            return session
                    .createQuery(query.select(post))
                    .getResultList();
        });
    }
}

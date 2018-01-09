package com.AndyRadulescu.store.controller;

import com.AndyRadulescu.store.mongoModel.Comment;
import com.AndyRadulescu.store.repository.CommentMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller class. Manipulates the crud operations for the Comment model.
 * This is different from the other 2 because it communicates with a noSQL
 * database.
 */
@Component
public class CommentMongoController {
    @Autowired
    private CommentMongoRepository commentMongoRepository;

    /**
     * Adds a comment to the database.
     *
     * @param newComment the comment that waits to be inserted.
     */
    public boolean addComment(Comment newComment) {
        commentMongoRepository.save(newComment);
        return true;
    }

    /**
     * Gets all the comments of a specific user.
     *
     * @param userId is the string for what the query looks for.
     */
    public List<Comment> getAllComments(String userId) {
        return commentMongoRepository.findCommentsByUserId(userId);
    }
}

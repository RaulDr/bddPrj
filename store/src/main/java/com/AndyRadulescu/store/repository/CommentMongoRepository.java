package com.AndyRadulescu.store.repository;

import com.AndyRadulescu.store.mongoModel.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMongoRepository extends MongoRepository<Comment, String> {
    List<Comment> findCommentsByUserId(String userId);
}

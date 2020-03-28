package com.zzx.dao;

import com.zzx.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDao {

    List<Comment> findAllParentCommentsByBlogId(Long blogId);

    Comment findById(Long id);

    void saveComment(Comment comment);

    List<Comment> findCommentsByParentId(Long parentId);
}

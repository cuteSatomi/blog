package com.zzx.service;

import com.zzx.pojo.Comment;

import java.util.List;

public interface CommentService {

    //查询所有评论
    List<Comment> findAllParentCommentsByBlogId(Long blogId);

    //根据留言的id查询
    Comment findById(Long id);

    //保存新增的评论
    void saveComment(Comment comment);
}

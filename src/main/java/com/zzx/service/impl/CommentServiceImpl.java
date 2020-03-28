package com.zzx.service.impl;

import com.zzx.dao.CommentDao;
import com.zzx.pojo.Comment;
import com.zzx.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;



    @Override
    public List<Comment> findAllParentCommentsByBlogId(Long blogId) {
        List<Comment> comments = commentDao.findAllParentCommentsByBlogId(blogId);
        return eachComment(comments);
    }

    @Override
    public Comment findById(Long id) {
        return commentDao.findById(id);
    }

    @Override
    public void saveComment(Comment comment) {
        //在业务层要做一些数据初始化
        //设置创建该评论的时间
        comment.setCreateTime(new Date());
        //判断该评论是否为顶级评论(parentCommentId不等于-1时,该评论才为顶级评论)
        Long parentCommentId = comment.getParentCommentId();
        if (parentCommentId != 1) {
            comment.setParentComment(commentDao.findById(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        commentDao.saveComment(comment);
    }

    /**
     * 循环每个顶级的评论节点
     *
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            //查询出所有顶级评论的子级评论
            List<Comment> replys = commentDao.findCommentsByParentId(comment.getId());
            //判断该顶级评论是否有子评论
            if (replys != null && replys.size() > 0) {
                for (Comment reply : replys) {
                    //设置子评论的父评论的昵称
                    reply.setParentNickname(comment.getNickname());
                    //循环迭代，找出子代，存放在tempReplys中
                    recursively(reply);
                }
                //修改顶级节点的reply集合为迭代处理后的集合
                comment.setReplyComments(tempReplys);
                //清除临时存放区
                tempReplys = new ArrayList<>();
            }else {
                //给没有子评论的父评论的replyComments赋初始值
                comment.setReplyComments(new ArrayList<>());
            }
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     *
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        List<Comment> replys = commentDao.findCommentsByParentId(comment.getId());
        if (replys != null && replys.size() > 0) {
            for (Comment reply : replys) {
                //设置子评论的父评论的昵称
                reply.setParentNickname(comment.getNickname());
                recursively(reply);
            }
        }
    }
}

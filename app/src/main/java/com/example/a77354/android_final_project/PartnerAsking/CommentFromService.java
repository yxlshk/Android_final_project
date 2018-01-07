package com.example.a77354.android_final_project.PartnerAsking;

/**
 * Created by 77354 on 2018/1/6.
 */

public class CommentFromService {
    String commentid, articleid, author, content, addtime;
    CommentFromService (String commentid, String articleid, String author, String content, String addtime) {
        this.commentid = commentid;
        this.articleid = articleid;
        this.author = author;
        this.content = content;
        this.addtime = addtime;
    }

    public String getCommentid () {
        return commentid;
    }
    public String getArticleid() {
        return articleid;
    }
    public String getAuthor() {
        return author;
    }
    public String getContent() {
        return content;
    }
    public String getAddtime() {
        return addtime;
    }
}

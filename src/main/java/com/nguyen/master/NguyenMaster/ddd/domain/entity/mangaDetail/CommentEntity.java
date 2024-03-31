package com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.auth.Users;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private BigInteger commentId;

    @Column(name = "content")
    private String content;

    @Column(name = "time_comment")
    private Timestamp timeComment;

    @Column(name = "like_comment")
    private Integer likeComment;

    @Column(name = "dislike_comment")
    private Integer dislikeComment;

    @Column(name = "sub_comment_id")
    private BigInteger subCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonBackReference
    private Users users;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", referencedColumnName = "story_id")
    @JsonBackReference
    private StoryEntity storyEntity;

    @Override
    public String toString() {
        return "CommentEntity{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                ", timeComment=" + timeComment +
                ", likeComment=" + likeComment +
                ", dislikeComment=" + dislikeComment +
                ", subCommentId=" + subCommentId +
                '}';
    }
}

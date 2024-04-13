package com.nguyen.master.NguyenMaster.ddd.domain.entity.home;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.AuthorEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.BaseEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.ChapterEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.CommentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "story")
public class StoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_id")
    private BigInteger storyId;

    @Column(name = "story_name")
    private String storyName;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "release_date")
    private Timestamp releaseDate;

    @Column(name = "status")
    private Integer status;

    @Column(name = "follow_number")
    private BigInteger followNumber;

    @Column(name = "like_number")
    private BigInteger likeNumber;


    @Column(name = "rating_point")
    private Integer ratingPoint;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "author_story",
            joinColumns = @JoinColumn(name = "story_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @Column(nullable = true)
    @JsonManagedReference
    private List<AuthorEntity> authorEntities;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "storyEntity")
    @Column(nullable = true)
    @JsonManagedReference
    private List<ChapterEntity> chapterEntities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "storyEntity")
    @Column(nullable = false)
    @JsonManagedReference
    private List<CommentEntity> commentEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_genre_fk", referencedColumnName = "story_genre_id")
    @JsonBackReference
    private StoryGenreEntity storyGenreEntity;

    @Override
    public String toString() {
        return "StoryEntity{" +
                "story_id=" + storyId +
                ", storyName='" + storyName + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", status=" + status +
                ", followNumber=" + followNumber +
                ", likeNumber=" + likeNumber +
                '}';
    }
}

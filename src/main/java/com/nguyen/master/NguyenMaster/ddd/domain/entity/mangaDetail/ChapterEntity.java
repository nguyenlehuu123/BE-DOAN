package com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.BaseEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chapter")
public class ChapterEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id")
    private Integer chapterId;

    @Column(name = "chapter_number")
    private Integer chapterNumber;

    @Column(name = "view_number")
    private Integer viewNumber;

    @Column(name = "status_key")
    private Integer statusKey;

    @Column(name = "release_date")
    private Timestamp releaseDate;

    @Column(name = "url_file")
    private String urlFile;

    @Column(name = "file_name")
    private String fileName;

    @ManyToOne()
    @JoinColumn(name = "story_id_fk", referencedColumnName = "story_id")
    @JsonBackReference
    private StoryEntity storyEntity;

}

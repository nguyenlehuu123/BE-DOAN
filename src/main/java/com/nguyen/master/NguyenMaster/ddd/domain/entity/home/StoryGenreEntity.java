package com.nguyen.master.NguyenMaster.ddd.domain.entity.home;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "story_genre")
public class StoryGenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_genre_id")
    private Integer storyGenreId;

    @Column(name = "story_genre_name")
    private String storyGenreName;

    @OneToMany(mappedBy = "storyGenreEntity", fetch = FetchType.LAZY)
    @Column(nullable = true)
    @JsonManagedReference
    private List<StoryEntity> storyEntities;

    @Override
    public String toString() {
        return "StoryGenreEntity{" +
                "storyGenreId=" + storyGenreId +
                ", storyGenreName='" + storyGenreName + '\'' +
                '}';
    }
}

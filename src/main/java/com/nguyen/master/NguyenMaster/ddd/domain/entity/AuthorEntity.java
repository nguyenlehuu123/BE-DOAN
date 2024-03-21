package com.nguyen.master.NguyenMaster.ddd.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private BigInteger authorId;

    @Column(name = "name")
    private String name;

    @Column(name = "pseudonym")
    private String pseudonym;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "author_story",
    joinColumns = @JoinColumn(name = "author_id"),
    inverseJoinColumns = @JoinColumn(name = "story_id"))
    @JsonBackReference
    private List<StoryEntity> storyEntities;

    @Override
    public String toString() {
        return "AuthorEntity{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", pseudonym='" + pseudonym + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

package com.nguyen.master.NguyenMaster.ddd.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

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

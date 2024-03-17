package com.nguyen.master.NguyenMaster.ddd.domain.entity.auth;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class Tokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(unique = true, name = "refresh_token")
    private String refreshToken;

    @Builder.Default
    private boolean revoked = false;

    @Builder.Default
    private boolean expired = false;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    public Users users;


}

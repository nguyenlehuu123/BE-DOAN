package com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rating")
public class RatingEntity {
    @EmbeddedId
    private RatingId id;

    @Column(name = "rating")
    private Float rating;

    @Override
    public String toString() {
        return "RatingEntity{" +
                "id=" + id +
                ", rating=" + rating +
                '}';
    }
}



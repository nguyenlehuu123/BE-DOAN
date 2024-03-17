package com.nguyen.master.NguyenMaster.ddd.domain.entity.home;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "option_header")
public class OptionHeaderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_header_id")
    private Integer optionHeaderId;

    @Column(name = "title")
    private String title;

    @Column(name = "order_sort")
    private Integer orderSort;

    @Column(name = "icon")
    private String icon;

    @Column(name = "link")
    private String link;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "optionHeaderEntity")
    @Column(nullable = true)
    @JsonManagedReference
    private List<OptionCategoryEntity> optionCategoryEntities;

    @Override
    public String toString() {
        return "OptionHeaderEntity{" +
                "optionHeaderId=" + optionHeaderId +
                ", title='" + title + '\'' +
                ", orderSort=" + orderSort +
                ", link='" + link + '\'' +
                '}';
    }
}

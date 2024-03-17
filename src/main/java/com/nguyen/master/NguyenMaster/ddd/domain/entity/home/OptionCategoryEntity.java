package com.nguyen.master.NguyenMaster.ddd.domain.entity.home;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "option_category")
public class OptionCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_category_id")
    private Integer optionCategoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "link_category")
    private String linkCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_header_id_fk", referencedColumnName = "option_header_id")
    @JsonBackReference
    private OptionHeaderEntity optionHeaderEntity;

    @Override
    public String toString() {
        return "OptionCategoryEntity{" +
                "optionCategoryId=" + optionCategoryId +
                ", categoryName='" + categoryName + '\'' +
                ", linkCategory='" + linkCategory + '\'' +
                '}';
    }
}

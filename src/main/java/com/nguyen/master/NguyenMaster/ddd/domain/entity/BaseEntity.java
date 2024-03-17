package com.nguyen.master.NguyenMaster.ddd.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@MappedSuperclass
public class BaseEntity {
    @Column(name = "creator")
    private String creator;

    @Column(name = "create_timestamp")
    private Timestamp createTimestamp;

    @Column(name = "updater")
    private String updater;

    @Column(name = "update_timestamp")
    private Timestamp updateTimestamp;

    @Column(name = "version_no")
    private BigInteger versionNo;

    @Column(name = "delete_flg")
    private Integer deleteFlg;
}

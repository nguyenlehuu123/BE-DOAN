package com.nguyen.master.NguyenMaster.ddd.domain.entity.readHistory;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "read_history")
public class ReadHistoryEntity {
    @EmbeddedId
    private ReadHistoryId id;

    private Timestamp readAt;

    @Override
    public String toString() {
        return "ReadHistoryEntity{" +
                "id=" + id +
                ", readAt='" + readAt + '\'' +
                '}';
    }
}

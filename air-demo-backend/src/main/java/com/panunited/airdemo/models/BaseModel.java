package com.panunited.airdemo.models;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseModel {
    private Long createdBy;
    private LocalDateTime createdDate;
    private Long updatedBy;
    private LocalDateTime updatedDate;

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        this.createdDate = LocalDateTime.now(ZoneOffset.UTC);
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        this.updatedDate = LocalDateTime.now(ZoneOffset.UTC);
    }
}

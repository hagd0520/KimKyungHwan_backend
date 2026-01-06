package com.exmaple.coding_test.support.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Enumerated(EnumType.STRING)
    private EntityStatus status = EntityStatus.ACTIVE;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void active() {
        this.status = EntityStatus.ACTIVE;
    }

    public void delete() {
        this.status = EntityStatus.DELETED;
    }

    public boolean isActive() {
        return this.status == EntityStatus.ACTIVE;
    }

    public boolean isDeleted() {
        return this.status == EntityStatus.DELETED;
    }
}

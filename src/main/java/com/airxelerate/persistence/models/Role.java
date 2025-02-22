package com.airxelerate.persistence.models;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.airxelerate.persistence.models.config.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

}

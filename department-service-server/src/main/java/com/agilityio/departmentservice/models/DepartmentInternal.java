package com.agilityio.departmentservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.Clock;
import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "builderInternal", buildMethodName = "buildInternal")
public class DepartmentInternal extends Department {

    @CreatedDate
    private Instant createdAt = Instant.now(Clock.systemUTC());

    @LastModifiedDate
    private Instant modifiedAt = Instant.now(Clock.systemUTC());

    @Version
    private Long version;
}

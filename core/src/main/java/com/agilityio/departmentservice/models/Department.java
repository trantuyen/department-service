package com.agilityio.departmentservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Department {

    /**
     * The department id
     */
    @Id
    private String id;

    /**
     * The department name
     */
    @NotBlank
    private String name;

    /**
     * The department phone number
     */
    @NotBlank
    private String phone;

    /**
     * The department address
     */
    private String address;

    public Department(String name) {
        this.name = name;
    }
}

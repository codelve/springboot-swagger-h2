package com.reach52.asssignment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
@Builder
public class CustomerDTO {

    private Long userId;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "DOB is mandatory")
    private Date dateofBirth;
    private String address;

}



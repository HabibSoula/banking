package com.eazybytes.accounts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Negative;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
//@Table(name = )
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "native")
    private Long customerId;

    private String name;

    private String email;

    //@Column(name = "")
    private String mobileNumber;

}

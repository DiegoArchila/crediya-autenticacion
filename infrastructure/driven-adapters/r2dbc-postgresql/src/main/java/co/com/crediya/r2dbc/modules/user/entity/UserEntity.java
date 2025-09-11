package co.com.crediya.r2dbc.modules.user.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table("users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @Column("id")
    private UUID id;

    private String firstname;

    private String lastname;

    private String email;


    @Column("dni_number")
    private String dniNumber;

    @Column("born_date")
    private LocalDate bornDate;

    private String phone;


    @Column("rol_id")
    private Integer rolId;

    @Column("base_salary")
    private BigDecimal baseSalary;

}
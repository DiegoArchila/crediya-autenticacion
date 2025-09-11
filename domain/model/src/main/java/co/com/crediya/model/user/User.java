package co.com.crediya.model.user;

import co.com.crediya.model.exception.ExceptionHandler;
import co.com.crediya.model.user.exception.UserErrorCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class User {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private String dniNumber;
    private LocalDate bornDate;
    private String phone;
    private Integer rolId;
    private BigDecimal baseSalary;

    //Constructor
    public User(){}

    public User(UUID id, String firstname, String lastname, String email,
                String dniNumber, LocalDate bornDate, String phone, Integer rolId,
                BigDecimal baseSalary) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.dniNumber = dniNumber;
        this.bornDate = bornDate;
        this.phone = phone;
        this.rolId = rolId;
        this.baseSalary = baseSalary;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDniNumber() {
        return dniNumber;
    }

    public void setDniNumber(String dniNumber) {
        this.dniNumber = dniNumber;
    }

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public void validate() {
        if (firstname == null || firstname.trim().isEmpty()) {
            throw new ExceptionHandler(UserErrorCode.FirstName_Empty);
        }
        if (lastname == null || lastname.trim().isEmpty()) {
            throw new ExceptionHandler(UserErrorCode.lastName_Empty);
        }
        if (email == null || email.trim().isEmpty()) {
            throw new ExceptionHandler(UserErrorCode.email_Empty);
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ExceptionHandler(UserErrorCode.email_Invalid);
        }
        if (baseSalary == null) {
            throw new ExceptionHandler(UserErrorCode.baseSalary_Empty);
        }
        if (baseSalary.compareTo(BigDecimal.ZERO) < 0) {
            throw new ExceptionHandler(UserErrorCode.baseSalary_Negative);
        }
        if (baseSalary.compareTo(new BigDecimal("15000000.00")) > 0) {
            throw new ExceptionHandler(UserErrorCode.baseSalary_ExceedsMaximum);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", dniNumber='" + dniNumber + '\'' +
                ", bornDate=" + bornDate +
                ", phone='" + phone + '\'' +
                ", rolId=" + rolId +
                ", baseSalary=" + baseSalary +
                '}';
    }
}

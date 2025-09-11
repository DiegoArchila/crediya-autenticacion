package co.com.crediya.model.user;

import co.com.crediya.model.exception.ExceptionHandler;
import co.com.crediya.model.user.exception.UserErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testConstructorAndGetters() {

        UUID id = UUID.randomUUID();
        User user = new User(id, "John", "Doe", "john.doe@email.com", "12345678", LocalDate.of(1990, 1, 1), "5551234", 1, new BigDecimal("1000000.00"));
        assertEquals(id, user.getId());
        assertEquals("John", user.getFirstname());
        assertEquals("Doe", user.getLastname());
        assertEquals("john.doe@email.com", user.getEmail());
        assertEquals("12345678", user.getDniNumber());
        assertEquals(LocalDate.of(1990, 1, 1), user.getBornDate());
        assertEquals("5551234", user.getPhone());
        assertEquals(1, user.getRolId());
        assertEquals(new BigDecimal("1000000.00"), user.getBaseSalary());

    }

    @Test
    void testSetters() {

        User user = new User(UUID.randomUUID(), "A", "B", "a@b.com", "1", LocalDate.now(), "2", 2, BigDecimal.ONE);
        user.setFirstname("Jane");
        user.setLastname("Smith");
        user.setEmail("jane.smith@email.com");
        user.setDniNumber("87654321");
        user.setBornDate(LocalDate.of(1985, 5, 5));
        user.setPhone("5554321");
        user.setRolId(3);
        user.setBaseSalary(new BigDecimal("2000000.00"));
        assertEquals("Jane", user.getFirstname());
        assertEquals("Smith", user.getLastname());
        assertEquals("jane.smith@email.com", user.getEmail());
        assertEquals("87654321", user.getDniNumber());
        assertEquals(LocalDate.of(1985, 5, 5), user.getBornDate());
        assertEquals("5554321", user.getPhone());
        assertEquals(3, user.getRolId());
        assertEquals(new BigDecimal("2000000.00"), user.getBaseSalary());

    }

    @Test
    void testValidateSuccess() {

        User user = new User(UUID.randomUUID(), "John", "Doe", "john.doe@email.com", "12345678", LocalDate.of(1990, 1, 1), "5551234", 1, new BigDecimal("1000000.00"));
        assertDoesNotThrow(user::validate);

    }

    @Test
    void testValidateFirstnameEmpty() {

        User user = new User(UUID.randomUUID(), "", "Doe", "john.doe@email.com", "12345678", LocalDate.of(1990, 1, 1), "5551234", 1, new BigDecimal("1000000.00"));
        ExceptionHandler ex = assertThrows(ExceptionHandler.class, user::validate);
        assertEquals(UserErrorCode.FirstName_Empty, ex.getEstadoError());

    }

    @Test
    void testValidateLastnameEmpty() {
        User user = new User(UUID.randomUUID(), "John", "", "john.doe@email.com", "12345678", LocalDate.of(1990, 1, 1), "5551234", 1, new BigDecimal("1000000.00"));
        ExceptionHandler ex = assertThrows(ExceptionHandler.class, user::validate);
        assertEquals(UserErrorCode.lastName_Empty, ex.getEstadoError());
    }

    @Test
    void testValidateEmailEmpty() {
        User user = new User(UUID.randomUUID(), "John", "Doe", "", "12345678", LocalDate.of(1990, 1, 1), "5551234", 1, new BigDecimal("1000000.00"));
        ExceptionHandler ex = assertThrows(ExceptionHandler.class, user::validate);
        assertEquals(UserErrorCode.email_Empty, ex.getEstadoError());
    }

    @Test
    void testValidateEmailInvalid() {
        User user = new User(UUID.randomUUID(), "John", "Doe", "invalid-email", "12345678", LocalDate.of(1990, 1, 1), "5551234", 1, new BigDecimal("1000000.00"));
        ExceptionHandler ex = assertThrows(ExceptionHandler.class, user::validate);
        assertEquals(UserErrorCode.email_Invalid, ex.getEstadoError());
    }

    @Test
    void testValidateBaseSalaryEmpty() {
        User user = new User(UUID.randomUUID(), "John", "Doe", "john.doe@email.com", "12345678", LocalDate.of(1990, 1, 1), "5551234", 1, null);
        ExceptionHandler ex = assertThrows(ExceptionHandler.class, user::validate);
        assertEquals(UserErrorCode.baseSalary_Empty, ex.getEstadoError());
    }

    @Test
    void testValidateBaseSalaryNegative() {
        User user = new User(UUID.randomUUID(), "John", "Doe", "john.doe@email.com", "12345678", LocalDate.of(1990, 1, 1), "5551234", 1, new BigDecimal("-1.00"));
        ExceptionHandler ex = assertThrows(ExceptionHandler.class, user::validate);
        assertEquals(UserErrorCode.baseSalary_Negative, ex.getEstadoError());
    }

    @Test
    void testValidateBaseSalaryExceedsMaximum() {
        User user = new User(UUID.randomUUID(), "John", "Doe", "john.doe@email.com", "12345678", LocalDate.of(1990, 1, 1), "5551234", 1, new BigDecimal("20000000.00"));
        ExceptionHandler ex = assertThrows(ExceptionHandler.class, user::validate);
        assertEquals(UserErrorCode.baseSalary_ExceedsMaximum, ex.getEstadoError());
    }

}

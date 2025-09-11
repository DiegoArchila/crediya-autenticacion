package co.com.crediya.api.modules.user.mapper;

import co.com.crediya.api.modules.user.dto.NewUserRequestDto;
import co.com.crediya.api.modules.user.dto.UserResponseDto;
import co.com.crediya.model.user.User;

public class UserMapper {

    public static User toDomain(NewUserRequestDto user) {
        User userDomain = new User();

        userDomain.setFirstname(user.getFirstname());
        userDomain.setLastname(user.getLastname());
        userDomain.setEmail(user.getEmail());
        userDomain.setDniNumber(user.getDniNumber());
        userDomain.setBornDate(user.getBornDate());
        userDomain.setPhone(user.getPhone());
        userDomain.setRolId(user.getRolId());
        userDomain.setBaseSalary(user.getBaseSalary());

        return userDomain;
    }

    public static UserResponseDto toUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();

        dto.setId(user.getId());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setDniNumber(user.getDniNumber());
        dto.setBornDate(user.getBornDate());
        dto.setPhone(user.getPhone());
        dto.setRolId(user.getRolId());
        dto.setBaseSalary(user.getBaseSalary());

        return dto;

    }

}

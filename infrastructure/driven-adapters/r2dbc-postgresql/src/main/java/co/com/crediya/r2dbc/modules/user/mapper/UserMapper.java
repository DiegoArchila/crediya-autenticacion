package co.com.crediya.r2dbc.modules.user.mapper;

import co.com.crediya.model.user.User;
import co.com.crediya.r2dbc.modules.user.entity.UserEntity;

public class UserMapper {

    public static User toDomain(UserEntity entity) {

        User user = new User();

        user.setId(entity.getId());
        user.setFirstname(entity.getFirstname());
        user.setLastname(entity.getLastname());
        user.setEmail(entity.getEmail());
        user.setDniNumber(entity.getDniNumber());
        user.setBornDate(entity.getBornDate());
        user.setPhone(entity.getPhone());
        user.setRolId(entity.getRolId());
        user.setBaseSalary(entity.getBaseSalary());

        return user;

    }

    public static UserEntity toEntity(User user) {

        return new UserEntity(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getDniNumber(),
                user.getBornDate(),
                user.getPhone(),
                user.getRolId(),
                user.getBaseSalary()
        );

    }
}

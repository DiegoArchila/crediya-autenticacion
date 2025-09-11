package co.com.crediya.model.user.exception;

import co.com.crediya.model.exception.MessageException;
import co.com.crediya.model.exception.TypeException;


public enum UserErrorCode implements MessageException {

    FirstName_Empty("Validation_FirstName_Empty","El campo nombres no puede estar vacío",
            400, TypeException.ValidationException.toString()),

    lastName_Empty("Validation_lastName_Empty","El campo apellidos no puede estar vacío",
            400, TypeException.ValidationException.toString()),

    id_AlreadyExists("Validation_id_AlreadyExists","El id ya está registrado",
            400, TypeException.BusinessException.toString()),

    documentIdentity_Empty("Validation_documentIdentity_Empty","El campo documento de identidad no puede estar vacío",
            400, TypeException.ValidationException.toString()),

    documentIdentity_AlreadyExists("Validation_documentIdentity_AlreadyExists","El documento de identidad ya está registrado",
            400, TypeException.BusinessException.toString()),

    email_Empty("Validation_email_Empty","El correo electrónico no puede estar vacío",
            400, TypeException.ValidationException.toString()),

    email_Invalid("Validation_email_Invalid","El correo electrónico no es válido",
            400, TypeException.ValidationException.toString()),

    email_AlreadyExists("Validation_email_AlreadyExists","El correo electrónico ya está registrado",
            400, TypeException.BusinessException.toString()),

    email_NotFound("Validation_email_NotFound","El correo electrónico no fue encontrado",
            404, TypeException.BusinessException.toString()),

    baseSalary_Empty("Validation_baseSalary_Empty","El salario base no puede estar vacío",
            400, TypeException.ValidationException.toString()),

    baseSalary_Negative("Validation_baseSalary_Negative","El salario base no puede ser menor de 0.00",
            400, TypeException.ValidationException.toString()),

    baseSalary_ExceedsMaximum("Validation_baseSalary_ExceedsMaximum","El salario base no puede ser mayor a 15000000.00",
            400, TypeException.ValidationException.toString());

    private final String code;
    private final String message;
    private final int httpStatus;
    private final String typeException;

    UserErrorCode(String code, String message, int httpStatus, String typeException) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
        this.typeException = typeException;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return httpStatus;
    }

    @Override
    public String getTypeException() {
        return typeException;
    }



}

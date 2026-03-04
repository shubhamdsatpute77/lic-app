package com.sds.backend.validators;

import com.sds.backend.dto.RegisterUserRequest;
import com.sds.backend.enums.UserRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class RegisterUserRequestValidator implements ConstraintValidator<ValidRegisterUserRequest, RegisterUserRequest> {
    @Override
    public boolean isValid(RegisterUserRequest request,
                           ConstraintValidatorContext context) {
        if (Objects.isNull(request)) return false;
        if (request.role() == UserRole.USER) {
            if (Objects.isNull(request.managerEmail()) || request.managerEmail().isBlank()) return false;
            return EmailValidator.getInstance().isValid(request.managerEmail());
        }
        return false;
    }
}

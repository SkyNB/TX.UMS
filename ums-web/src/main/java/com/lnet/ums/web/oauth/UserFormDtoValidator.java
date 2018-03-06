package com.lnet.ums.web.oauth;

import com.lnet.ums.contract.model.oauth.Privilege;
import com.lnet.ums.contract.model.oauth.UserFormDto;
import com.lnet.ums.contract.oauth.OauthUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 *
 *
 * @author lxf
 */
@Component
public class UserFormDtoValidator implements Validator {


    @Autowired
    private OauthUserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserFormDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserFormDto formDto = (UserFormDto) target;

        validateUsername(errors, formDto);
        validatePassword(errors, formDto);
        validatePrivileges(errors, formDto);
    }

    private void validatePrivileges(Errors errors, UserFormDto formDto) {
        final List<Privilege> privileges = formDto.getPrivileges();
        if (privileges == null || privileges.isEmpty()) {
            errors.rejectValue("privileges", null, "Privileges is required");
        }
    }

    private void validatePassword(Errors errors, UserFormDto formDto) {
        final String password = formDto.getPassword();
        if (StringUtils.isEmpty(password)) {
            errors.rejectValue("password", null, "Password is required");
        }
    }

    private void validateUsername(Errors errors, UserFormDto formDto) {
        final String username = formDto.getUsername();
        if (StringUtils.isEmpty(username)) {
            errors.rejectValue("username", null, "Username is required");
            return;
        }

        boolean existed = userService.isExistedUsername(username);
        if (existed) {
            errors.rejectValue("username", null, "Username already existed");
        }

    }
}

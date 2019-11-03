package com.rest.api.marshallers.internal;

import com.rest.api.dto.internal.ContactDto;
import com.rest.api.dto.internal.EmailDto;
import com.rest.api.dto.internal.UserDto;
import com.rest.api.dto.internal.UserInternalSchemaDto;

import java.util.List;

public class UserInternalSchemaMarshaller {
    public static UserInternalSchemaDto userInternalSchemaDto(ContactDto contactDto,
                                                              List<EmailDto> emailsDto,
                                                              UserDto userDto) {

        for (EmailDto rowEmail : emailsDto) {
            rowEmail.setContactId(contactDto.getId());
        }

        UserInternalSchemaDto userInternalSchemaDto = new UserInternalSchemaDto(
                contactDto,
                emailsDto,
                userDto);

        return userInternalSchemaDto;
    }
}

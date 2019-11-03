package com.rest.api.dto.output;

import com.rest.api.dto.internal.ContactDto;
import com.rest.api.dto.internal.EmailDto;
import com.rest.api.dto.internal.UserDto;

import java.util.List;

public class UserInterfaceMarshaller {
    public static UserInterfaceDto userInterfaceDto(ContactDto contactDto,
                                                    List<EmailDto> emailDto,
                                                    UserDto userDto) {
        return new UserInterfaceDto(
                userDto.getId(),
                contactDto.getFirstName(),
                contactDto.getLastName(),
                contactDto.getBirthDate(),
                userDto.getStatus(),
                emailDto);
    }
}

package com.urbanlance.user.mapper;

import com.urbanlance.user.dto.AddProfileRequest;
import com.urbanlance.user.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(AddProfileRequest request){
        return new Address(
                request.country(),
                request.state(),
                request.district(),
                request.localAddress(),
                request.pinNumber()
        );
    }
}

package com.urbanlance.user.service;

import com.urbanlance.user.Exception.ProfileNotFoundException;
import com.urbanlance.user.dto.AddProfileRequest;
import com.urbanlance.user.dto.PatchProfileRequestDto;
import com.urbanlance.user.dto.ProfileResponse;
import com.urbanlance.user.mapper.AddressMapper;
import com.urbanlance.user.mapper.ProfileMapper;
import com.urbanlance.user.model.Address;
import com.urbanlance.user.model.Profile;
import com.urbanlance.user.repository.AddressRepository;
import com.urbanlance.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserProfileRepository userProfileRepository;
    private final AddressRepository addressRepository;
    private final TransactionTemplate transactionTemplate;
    private final RoutingKafkaTemplate kafkaTemplate;
    private final ModelMapper modelMapper;
    private final ProfileMapper profileMapper;
    private final AddressMapper addressMapper;

    public ProfileResponse addProfile(AddProfileRequest request,String authId){
         Optional<Profile> optional = userProfileRepository
                .findByAuthId(authId);
         if(optional.isEmpty()){
//             UserCreateEvent failed think later what to do
             throw new ProfileNotFoundException("");
         }
         Profile profile = optional.get();
         profile.setGender(request.gender());
         profile.setIndustries(request.industries());
         if(request.headline() != null){
             profile.setHeadline(request.headline());
         }
         Address address = addressMapper.toEntity(request);
        Profile savedProfile  = transactionTemplate.execute(status -> {
             Address savedAddress = addressRepository.save(address);
             profile.setAddress(AggregateReference.to(savedAddress.getId()));
             return userProfileRepository.save(profile);
         });

        return modelMapper.map(savedProfile, ProfileResponse.class);
    }

    public ProfileResponse patchProfile(PatchProfileRequestDto request, String authId){
        Optional<Profile> optional = userProfileRepository
                .findByAuthId(authId);
        if(optional.isEmpty()){
            throw new ProfileNotFoundException("could not find profile with sub"+authId);
        }
        Profile profile = optional.get();
        if(request.headline() != null && !request.headline().isBlank()){
            profile.setHeadline(request.headline());
        }
        if(request.preferredName() != null && !request.preferredName().isBlank()){
            profile.setPreferredName(request.preferredName());
        }
        if(request.gender() != null){
            profile.setGender(request.gender());
        }
        if (request.industries() != null && !request.industries().isEmpty()){
            profile.setIndustries(request.industries());
        }

        Profile savedProfile = transactionTemplate.execute(status -> {
            return userProfileRepository.save(profile);
        });

        return modelMapper.map(savedProfile, ProfileResponse.class);
    }

    public void deleteProfile(String authId){
        Optional<Profile> optional = userProfileRepository
                .findByAuthId(authId);
        if(optional.isEmpty()){
            throw new ProfileNotFoundException("could not find profile with sub"+authId);
        }
        Profile profile = optional.get();

        profile.setDeletedAt(Instant.now());
        profile.setDeleted(true);
        Profile savedProfile = transactionTemplate.execute(status -> {
            return userProfileRepository.save(profile);
        });
    }
}

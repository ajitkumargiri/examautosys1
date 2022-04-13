package com.nscs.examautosys.service.mapper;

import com.nscs.examautosys.domain.Address;
import com.nscs.examautosys.domain.University;
import com.nscs.examautosys.service.dto.AddressDTO;
import com.nscs.examautosys.service.dto.UniversityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link University} and its DTO {@link UniversityDTO}.
 */
@Mapper(componentModel = "spring")
public interface UniversityMapper extends EntityMapper<UniversityDTO, University> {
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    UniversityDTO toDto(University s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);
}

package com.nscs.examautosys.service.mapper;

import com.nscs.examautosys.domain.Address;
import com.nscs.examautosys.domain.College;
import com.nscs.examautosys.domain.University;
import com.nscs.examautosys.service.dto.AddressDTO;
import com.nscs.examautosys.service.dto.CollegeDTO;
import com.nscs.examautosys.service.dto.UniversityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link College} and its DTO {@link CollegeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CollegeMapper extends EntityMapper<CollegeDTO, College> {
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    @Mapping(target = "university", source = "university", qualifiedByName = "universityId")
    CollegeDTO toDto(College s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);

    @Named("universityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UniversityDTO toDtoUniversityId(University university);
}

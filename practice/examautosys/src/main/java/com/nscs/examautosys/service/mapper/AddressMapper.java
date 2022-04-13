package com.nscs.examautosys.service.mapper;

import com.nscs.examautosys.domain.Address;
import com.nscs.examautosys.service.dto.AddressDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {}

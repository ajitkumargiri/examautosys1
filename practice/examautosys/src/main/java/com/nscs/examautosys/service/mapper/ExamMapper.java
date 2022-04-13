package com.nscs.examautosys.service.mapper;

import com.nscs.examautosys.domain.Address;
import com.nscs.examautosys.domain.Exam;
import com.nscs.examautosys.domain.Session;
import com.nscs.examautosys.service.dto.AddressDTO;
import com.nscs.examautosys.service.dto.ExamDTO;
import com.nscs.examautosys.service.dto.SessionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exam} and its DTO {@link ExamDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExamMapper extends EntityMapper<ExamDTO, Exam> {
    @Mapping(target = "correspondingAddress", source = "correspondingAddress", qualifiedByName = "addressId")
    @Mapping(target = "permanentAddress", source = "permanentAddress", qualifiedByName = "addressId")
    @Mapping(target = "session", source = "session", qualifiedByName = "sessionId")
    ExamDTO toDto(Exam s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);

    @Named("sessionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SessionDTO toDtoSessionId(Session session);
}

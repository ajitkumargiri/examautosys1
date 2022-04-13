package com.nscs.examautosys.service.mapper;

import com.nscs.examautosys.domain.Address;
import com.nscs.examautosys.domain.Exam;
import com.nscs.examautosys.domain.ExamCenter;
import com.nscs.examautosys.service.dto.AddressDTO;
import com.nscs.examautosys.service.dto.ExamCenterDTO;
import com.nscs.examautosys.service.dto.ExamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExamCenter} and its DTO {@link ExamCenterDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExamCenterMapper extends EntityMapper<ExamCenterDTO, ExamCenter> {
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    @Mapping(target = "exam", source = "exam", qualifiedByName = "examId")
    ExamCenterDTO toDto(ExamCenter s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);

    @Named("examId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExamDTO toDtoExamId(Exam exam);
}

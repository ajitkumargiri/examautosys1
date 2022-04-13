package com.nscs.examautosys.service.mapper;

import com.nscs.examautosys.domain.Address;
import com.nscs.examautosys.domain.Exam;
import com.nscs.examautosys.domain.ExamApplicationForm;
import com.nscs.examautosys.domain.ExamCenter;
import com.nscs.examautosys.domain.Student;
import com.nscs.examautosys.service.dto.AddressDTO;
import com.nscs.examautosys.service.dto.ExamApplicationFormDTO;
import com.nscs.examautosys.service.dto.ExamCenterDTO;
import com.nscs.examautosys.service.dto.ExamDTO;
import com.nscs.examautosys.service.dto.StudentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExamApplicationForm} and its DTO {@link ExamApplicationFormDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExamApplicationFormMapper extends EntityMapper<ExamApplicationFormDTO, ExamApplicationForm> {
    @Mapping(target = "correspondingAddress", source = "correspondingAddress", qualifiedByName = "addressId")
    @Mapping(target = "student", source = "student", qualifiedByName = "studentId")
    @Mapping(target = "exam", source = "exam", qualifiedByName = "examId")
    @Mapping(target = "examCenter", source = "examCenter", qualifiedByName = "examCenterId")
    ExamApplicationFormDTO toDto(ExamApplicationForm s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);

    @Named("studentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StudentDTO toDtoStudentId(Student student);

    @Named("examId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExamDTO toDtoExamId(Exam exam);

    @Named("examCenterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExamCenterDTO toDtoExamCenterId(ExamCenter examCenter);
}

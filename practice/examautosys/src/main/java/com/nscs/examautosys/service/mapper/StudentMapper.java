package com.nscs.examautosys.service.mapper;

import com.nscs.examautosys.domain.AcademicBatch;
import com.nscs.examautosys.domain.Student;
import com.nscs.examautosys.service.dto.AcademicBatchDTO;
import com.nscs.examautosys.service.dto.StudentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Student} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring")
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {
    @Mapping(target = "academicBatch", source = "academicBatch", qualifiedByName = "academicBatchId")
    StudentDTO toDto(Student s);

    @Named("academicBatchId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AcademicBatchDTO toDtoAcademicBatchId(AcademicBatch academicBatch);
}

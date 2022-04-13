package com.nscs.examautosys.service.mapper;

import com.nscs.examautosys.domain.AcademicBatch;
import com.nscs.examautosys.domain.Session;
import com.nscs.examautosys.service.dto.AcademicBatchDTO;
import com.nscs.examautosys.service.dto.SessionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Session} and its DTO {@link SessionDTO}.
 */
@Mapper(componentModel = "spring")
public interface SessionMapper extends EntityMapper<SessionDTO, Session> {
    @Mapping(target = "academicBatch", source = "academicBatch", qualifiedByName = "academicBatchId")
    SessionDTO toDto(Session s);

    @Named("academicBatchId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AcademicBatchDTO toDtoAcademicBatchId(AcademicBatch academicBatch);
}

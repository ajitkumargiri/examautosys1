package com.nscs.examautosys.service.mapper;

import com.nscs.examautosys.domain.Session;
import com.nscs.examautosys.domain.SubjectPaper;
import com.nscs.examautosys.service.dto.SessionDTO;
import com.nscs.examautosys.service.dto.SubjectPaperDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubjectPaper} and its DTO {@link SubjectPaperDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubjectPaperMapper extends EntityMapper<SubjectPaperDTO, SubjectPaper> {
    @Mapping(target = "session", source = "session", qualifiedByName = "sessionId")
    SubjectPaperDTO toDto(SubjectPaper s);

    @Named("sessionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SessionDTO toDtoSessionId(Session session);
}

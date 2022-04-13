package com.nscs.examautosys.service.mapper;

import com.nscs.examautosys.domain.Branch;
import com.nscs.examautosys.domain.Course;
import com.nscs.examautosys.service.dto.BranchDTO;
import com.nscs.examautosys.service.dto.CourseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Branch} and its DTO {@link BranchDTO}.
 */
@Mapper(componentModel = "spring")
public interface BranchMapper extends EntityMapper<BranchDTO, Branch> {
    @Mapping(target = "course", source = "course", qualifiedByName = "courseId")
    BranchDTO toDto(Branch s);

    @Named("courseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CourseDTO toDtoCourseId(Course course);
}

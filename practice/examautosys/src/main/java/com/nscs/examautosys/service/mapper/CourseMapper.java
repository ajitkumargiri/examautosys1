package com.nscs.examautosys.service.mapper;

import com.nscs.examautosys.domain.College;
import com.nscs.examautosys.domain.Course;
import com.nscs.examautosys.service.dto.CollegeDTO;
import com.nscs.examautosys.service.dto.CourseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Course} and its DTO {@link CourseDTO}.
 */
@Mapper(componentModel = "spring")
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {
    @Mapping(target = "college", source = "college", qualifiedByName = "collegeId")
    CourseDTO toDto(Course s);

    @Named("collegeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CollegeDTO toDtoCollegeId(College college);
}

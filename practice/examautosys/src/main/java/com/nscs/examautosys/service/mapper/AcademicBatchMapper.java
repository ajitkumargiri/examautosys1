package com.nscs.examautosys.service.mapper;

import com.nscs.examautosys.domain.AcademicBatch;
import com.nscs.examautosys.domain.Branch;
import com.nscs.examautosys.service.dto.AcademicBatchDTO;
import com.nscs.examautosys.service.dto.BranchDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AcademicBatch} and its DTO {@link AcademicBatchDTO}.
 */
@Mapper(componentModel = "spring")
public interface AcademicBatchMapper extends EntityMapper<AcademicBatchDTO, AcademicBatch> {
    @Mapping(target = "branch", source = "branch", qualifiedByName = "branchId")
    AcademicBatchDTO toDto(AcademicBatch s);

    @Named("branchId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BranchDTO toDtoBranchId(Branch branch);
}

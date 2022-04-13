package com.nscs.examautosys.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.nscs.examautosys.domain.AcademicBatch} entity.
 */
public class AcademicBatchDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String code;

    @NotNull
    private Integer academicStartYear;

    @NotNull
    private Integer academicEndYear;

    private BranchDTO branch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAcademicStartYear() {
        return academicStartYear;
    }

    public void setAcademicStartYear(Integer academicStartYear) {
        this.academicStartYear = academicStartYear;
    }

    public Integer getAcademicEndYear() {
        return academicEndYear;
    }

    public void setAcademicEndYear(Integer academicEndYear) {
        this.academicEndYear = academicEndYear;
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcademicBatchDTO)) {
            return false;
        }

        AcademicBatchDTO academicBatchDTO = (AcademicBatchDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, academicBatchDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcademicBatchDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", academicStartYear=" + getAcademicStartYear() +
            ", academicEndYear=" + getAcademicEndYear() +
            ", branch=" + getBranch() +
            "}";
    }
}

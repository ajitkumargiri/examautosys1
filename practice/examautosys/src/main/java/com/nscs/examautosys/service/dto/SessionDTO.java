package com.nscs.examautosys.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.nscs.examautosys.domain.Session} entity.
 */
public class SessionDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String code;

    private LocalDate startDate;

    private LocalDate endDate;

    private AcademicBatchDTO academicBatch;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public AcademicBatchDTO getAcademicBatch() {
        return academicBatch;
    }

    public void setAcademicBatch(AcademicBatchDTO academicBatch) {
        this.academicBatch = academicBatch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionDTO)) {
            return false;
        }

        SessionDTO sessionDTO = (SessionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sessionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SessionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", academicBatch=" + getAcademicBatch() +
            "}";
    }
}

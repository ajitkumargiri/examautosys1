package com.nscs.examautosys.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.nscs.examautosys.domain.ExamCenter} entity.
 */
public class ExamCenterDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String code;

    private AddressDTO address;

    private ExamDTO exam;

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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public ExamDTO getExam() {
        return exam;
    }

    public void setExam(ExamDTO exam) {
        this.exam = exam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamCenterDTO)) {
            return false;
        }

        ExamCenterDTO examCenterDTO = (ExamCenterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, examCenterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamCenterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", address=" + getAddress() +
            ", exam=" + getExam() +
            "}";
    }
}

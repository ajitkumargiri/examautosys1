package com.nscs.examautosys.service.dto;

import com.nscs.examautosys.domain.enumeration.ExamType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.nscs.examautosys.domain.Exam} entity.
 */
public class ExamDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private ExamType type;

    private String code;

    private Instant date;

    private LocalDate startTime;

    private LocalDate endTime;

    private AddressDTO correspondingAddress;

    private AddressDTO permanentAddress;

    private SessionDTO session;

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

    public ExamType getType() {
        return type;
    }

    public void setType(ExamType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public AddressDTO getCorrespondingAddress() {
        return correspondingAddress;
    }

    public void setCorrespondingAddress(AddressDTO correspondingAddress) {
        this.correspondingAddress = correspondingAddress;
    }

    public AddressDTO getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(AddressDTO permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public SessionDTO getSession() {
        return session;
    }

    public void setSession(SessionDTO session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamDTO)) {
            return false;
        }

        ExamDTO examDTO = (ExamDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, examDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", code='" + getCode() + "'" +
            ", date='" + getDate() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", correspondingAddress=" + getCorrespondingAddress() +
            ", permanentAddress=" + getPermanentAddress() +
            ", session=" + getSession() +
            "}";
    }
}

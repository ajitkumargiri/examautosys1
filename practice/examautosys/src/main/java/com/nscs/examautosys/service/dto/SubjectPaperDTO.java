package com.nscs.examautosys.service.dto;

import com.nscs.examautosys.domain.enumeration.SubjectPaperType;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.nscs.examautosys.domain.SubjectPaper} entity.
 */
public class SubjectPaperDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String code;

    private SubjectPaperType type;

    private Integer fullMark;

    private Integer passMark;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SubjectPaperType getType() {
        return type;
    }

    public void setType(SubjectPaperType type) {
        this.type = type;
    }

    public Integer getFullMark() {
        return fullMark;
    }

    public void setFullMark(Integer fullMark) {
        this.fullMark = fullMark;
    }

    public Integer getPassMark() {
        return passMark;
    }

    public void setPassMark(Integer passMark) {
        this.passMark = passMark;
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
        if (!(o instanceof SubjectPaperDTO)) {
            return false;
        }

        SubjectPaperDTO subjectPaperDTO = (SubjectPaperDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subjectPaperDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubjectPaperDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", type='" + getType() + "'" +
            ", fullMark=" + getFullMark() +
            ", passMark=" + getPassMark() +
            ", session=" + getSession() +
            "}";
    }
}

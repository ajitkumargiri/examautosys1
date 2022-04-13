package com.nscs.examautosys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscs.examautosys.domain.enumeration.SubjectPaperType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SubjectPaper.
 */
@Entity
@Table(name = "subject_paper")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubjectPaper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SubjectPaperType type;

    @Column(name = "full_mark")
    private Integer fullMark;

    @Column(name = "pass_mark")
    private Integer passMark;

    @ManyToOne
    @JsonIgnoreProperties(value = { "exams", "subjectPapers", "academicBatch" }, allowSetters = true)
    private Session session;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubjectPaper id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SubjectPaper name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public SubjectPaper code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SubjectPaperType getType() {
        return this.type;
    }

    public SubjectPaper type(SubjectPaperType type) {
        this.setType(type);
        return this;
    }

    public void setType(SubjectPaperType type) {
        this.type = type;
    }

    public Integer getFullMark() {
        return this.fullMark;
    }

    public SubjectPaper fullMark(Integer fullMark) {
        this.setFullMark(fullMark);
        return this;
    }

    public void setFullMark(Integer fullMark) {
        this.fullMark = fullMark;
    }

    public Integer getPassMark() {
        return this.passMark;
    }

    public SubjectPaper passMark(Integer passMark) {
        this.setPassMark(passMark);
        return this;
    }

    public void setPassMark(Integer passMark) {
        this.passMark = passMark;
    }

    public Session getSession() {
        return this.session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public SubjectPaper session(Session session) {
        this.setSession(session);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubjectPaper)) {
            return false;
        }
        return id != null && id.equals(((SubjectPaper) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubjectPaper{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", type='" + getType() + "'" +
            ", fullMark=" + getFullMark() +
            ", passMark=" + getPassMark() +
            "}";
    }
}

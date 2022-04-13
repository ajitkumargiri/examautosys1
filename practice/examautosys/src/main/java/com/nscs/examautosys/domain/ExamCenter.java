package com.nscs.examautosys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ExamCenter.
 */
@Entity
@Table(name = "exam_center")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExamCenter implements Serializable {

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

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "examCenter")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "correspondingAddress", "student", "exam", "examCenter" }, allowSetters = true)
    private Set<ExamApplicationForm> examApplicationForms = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "correspondingAddress", "permanentAddress", "applicationForms", "examCenters", "session" },
        allowSetters = true
    )
    private Exam exam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ExamCenter id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ExamCenter name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public ExamCenter code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ExamCenter address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Set<ExamApplicationForm> getExamApplicationForms() {
        return this.examApplicationForms;
    }

    public void setExamApplicationForms(Set<ExamApplicationForm> examApplicationForms) {
        if (this.examApplicationForms != null) {
            this.examApplicationForms.forEach(i -> i.setExamCenter(null));
        }
        if (examApplicationForms != null) {
            examApplicationForms.forEach(i -> i.setExamCenter(this));
        }
        this.examApplicationForms = examApplicationForms;
    }

    public ExamCenter examApplicationForms(Set<ExamApplicationForm> examApplicationForms) {
        this.setExamApplicationForms(examApplicationForms);
        return this;
    }

    public ExamCenter addExamApplicationForm(ExamApplicationForm examApplicationForm) {
        this.examApplicationForms.add(examApplicationForm);
        examApplicationForm.setExamCenter(this);
        return this;
    }

    public ExamCenter removeExamApplicationForm(ExamApplicationForm examApplicationForm) {
        this.examApplicationForms.remove(examApplicationForm);
        examApplicationForm.setExamCenter(null);
        return this;
    }

    public Exam getExam() {
        return this.exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public ExamCenter exam(Exam exam) {
        this.setExam(exam);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamCenter)) {
            return false;
        }
        return id != null && id.equals(((ExamCenter) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamCenter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}

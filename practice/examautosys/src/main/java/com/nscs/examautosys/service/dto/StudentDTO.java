package com.nscs.examautosys.service.dto;

import com.nscs.examautosys.domain.enumeration.BloodGroup;
import com.nscs.examautosys.domain.enumeration.Gender;
import com.nscs.examautosys.domain.enumeration.MaritialStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.nscs.examautosys.domain.Student} entity.
 */
public class StudentDTO implements Serializable {

    private Long id;

    private String regNumber;

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    private Instant dob;

    private String fatherName;

    private String motherName;

    @NotNull
    private String email;

    @NotNull
    private String mobileNumber;

    @NotNull
    private String nationality;

    private Gender gender;

    private String religion;

    private String catagory;

    private MaritialStatus maritialStatus;

    @NotNull
    private String adharNumber;

    private BloodGroup bloodGroup;

    private String fatherMobileNumber;

    private String fatherEmailId;

    @Lob
    private byte[] image;

    private String imageContentType;

    @Lob
    private byte[] signature;

    private String signatureContentType;

    @Lob
    private byte[] adhar;

    private String adharContentType;
    private AcademicBatchDTO academicBatch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Instant getDob() {
        return dob;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public MaritialStatus getMaritialStatus() {
        return maritialStatus;
    }

    public void setMaritialStatus(MaritialStatus maritialStatus) {
        this.maritialStatus = maritialStatus;
    }

    public String getAdharNumber() {
        return adharNumber;
    }

    public void setAdharNumber(String adharNumber) {
        this.adharNumber = adharNumber;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getFatherMobileNumber() {
        return fatherMobileNumber;
    }

    public void setFatherMobileNumber(String fatherMobileNumber) {
        this.fatherMobileNumber = fatherMobileNumber;
    }

    public String getFatherEmailId() {
        return fatherEmailId;
    }

    public void setFatherEmailId(String fatherEmailId) {
        this.fatherEmailId = fatherEmailId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureContentType() {
        return signatureContentType;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }

    public byte[] getAdhar() {
        return adhar;
    }

    public void setAdhar(byte[] adhar) {
        this.adhar = adhar;
    }

    public String getAdharContentType() {
        return adharContentType;
    }

    public void setAdharContentType(String adharContentType) {
        this.adharContentType = adharContentType;
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
        if (!(o instanceof StudentDTO)) {
            return false;
        }

        StudentDTO studentDTO = (StudentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, studentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentDTO{" +
            "id=" + getId() +
            ", regNumber='" + getRegNumber() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", dob='" + getDob() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", motherName='" + getMotherName() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", gender='" + getGender() + "'" +
            ", religion='" + getReligion() + "'" +
            ", catagory='" + getCatagory() + "'" +
            ", maritialStatus='" + getMaritialStatus() + "'" +
            ", adharNumber='" + getAdharNumber() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", fatherMobileNumber='" + getFatherMobileNumber() + "'" +
            ", fatherEmailId='" + getFatherEmailId() + "'" +
            ", image='" + getImage() + "'" +
            ", signature='" + getSignature() + "'" +
            ", adhar='" + getAdhar() + "'" +
            ", academicBatch=" + getAcademicBatch() +
            "}";
    }
}

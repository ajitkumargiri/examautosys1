package com.nscs.examautosys.service.dto;

import com.nscs.examautosys.domain.enumeration.BloodGroup;
import com.nscs.examautosys.domain.enumeration.Gender;
import com.nscs.examautosys.domain.enumeration.IdentityType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.nscs.examautosys.domain.ExamApplicationForm} entity.
 */
public class ExamApplicationFormDTO implements Serializable {

    private Long id;

    private String regNumber;

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    private Instant dob;

    private String fatherName;

    @NotNull
    private String email;

    @NotNull
    private String mobileNumber;

    @NotNull
    private String nationality;

    private Gender gender;

    private String religion;

    @NotNull
    private String adharNumber;

    private BloodGroup bloodGroup;

    private Boolean isApproved;

    private String catagory;

    @NotNull
    private IdentityType identityType;

    @NotNull
    private String identityNumber;

    @Lob
    private byte[] image;

    private String imageContentType;

    @Lob
    private byte[] signature;

    private String signatureContentType;

    @Lob
    private byte[] adhar;

    private String adharContentType;
    private String imagePath;

    private String signPath;

    private String adharPath;

    private AddressDTO correspondingAddress;

    private StudentDTO student;

    private ExamDTO exam;

    private ExamCenterDTO examCenter;

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

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public IdentityType getIdentityType() {
        return identityType;
    }

    public void setIdentityType(IdentityType identityType) {
        this.identityType = identityType;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSignPath() {
        return signPath;
    }

    public void setSignPath(String signPath) {
        this.signPath = signPath;
    }

    public String getAdharPath() {
        return adharPath;
    }

    public void setAdharPath(String adharPath) {
        this.adharPath = adharPath;
    }

    public AddressDTO getCorrespondingAddress() {
        return correspondingAddress;
    }

    public void setCorrespondingAddress(AddressDTO correspondingAddress) {
        this.correspondingAddress = correspondingAddress;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public ExamDTO getExam() {
        return exam;
    }

    public void setExam(ExamDTO exam) {
        this.exam = exam;
    }

    public ExamCenterDTO getExamCenter() {
        return examCenter;
    }

    public void setExamCenter(ExamCenterDTO examCenter) {
        this.examCenter = examCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamApplicationFormDTO)) {
            return false;
        }

        ExamApplicationFormDTO examApplicationFormDTO = (ExamApplicationFormDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, examApplicationFormDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamApplicationFormDTO{" +
            "id=" + getId() +
            ", regNumber='" + getRegNumber() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", dob='" + getDob() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", gender='" + getGender() + "'" +
            ", religion='" + getReligion() + "'" +
            ", adharNumber='" + getAdharNumber() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            ", catagory='" + getCatagory() + "'" +
            ", identityType='" + getIdentityType() + "'" +
            ", identityNumber='" + getIdentityNumber() + "'" +
            ", image='" + getImage() + "'" +
            ", signature='" + getSignature() + "'" +
            ", adhar='" + getAdhar() + "'" +
            ", imagePath='" + getImagePath() + "'" +
            ", signPath='" + getSignPath() + "'" +
            ", adharPath='" + getAdharPath() + "'" +
            ", correspondingAddress=" + getCorrespondingAddress() +
            ", student=" + getStudent() +
            ", exam=" + getExam() +
            ", examCenter=" + getExamCenter() +
            "}";
    }
}

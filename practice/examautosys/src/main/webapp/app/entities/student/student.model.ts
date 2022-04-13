import dayjs from 'dayjs/esm';
import { IExamApplicationForm } from 'app/entities/exam-application-form/exam-application-form.model';
import { IAcademicBatch } from 'app/entities/academic-batch/academic-batch.model';
import { Gender } from 'app/entities/enumerations/gender.model';
import { MaritialStatus } from 'app/entities/enumerations/maritial-status.model';
import { BloodGroup } from 'app/entities/enumerations/blood-group.model';

export interface IStudent {
  id?: number;
  regNumber?: string | null;
  firstName?: string;
  middleName?: string | null;
  lastName?: string;
  dob?: dayjs.Dayjs | null;
  fatherName?: string | null;
  motherName?: string | null;
  email?: string;
  mobileNumber?: string;
  nationality?: string;
  gender?: Gender | null;
  religion?: string | null;
  catagory?: string | null;
  maritialStatus?: MaritialStatus | null;
  adharNumber?: string;
  bloodGroup?: BloodGroup | null;
  fatherMobileNumber?: string | null;
  fatherEmailId?: string | null;
  imageContentType?: string | null;
  image?: string | null;
  signatureContentType?: string | null;
  signature?: string | null;
  adharContentType?: string | null;
  adhar?: string | null;
  examApplicationForms?: IExamApplicationForm[] | null;
  academicBatch?: IAcademicBatch | null;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public regNumber?: string | null,
    public firstName?: string,
    public middleName?: string | null,
    public lastName?: string,
    public dob?: dayjs.Dayjs | null,
    public fatherName?: string | null,
    public motherName?: string | null,
    public email?: string,
    public mobileNumber?: string,
    public nationality?: string,
    public gender?: Gender | null,
    public religion?: string | null,
    public catagory?: string | null,
    public maritialStatus?: MaritialStatus | null,
    public adharNumber?: string,
    public bloodGroup?: BloodGroup | null,
    public fatherMobileNumber?: string | null,
    public fatherEmailId?: string | null,
    public imageContentType?: string | null,
    public image?: string | null,
    public signatureContentType?: string | null,
    public signature?: string | null,
    public adharContentType?: string | null,
    public adhar?: string | null,
    public examApplicationForms?: IExamApplicationForm[] | null,
    public academicBatch?: IAcademicBatch | null
  ) {}
}

export function getStudentIdentifier(student: IStudent): number | undefined {
  return student.id;
}

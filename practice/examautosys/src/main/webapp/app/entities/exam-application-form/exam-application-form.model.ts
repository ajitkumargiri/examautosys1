import dayjs from 'dayjs/esm';
import { IAddress } from 'app/entities/address/address.model';
import { IStudent } from 'app/entities/student/student.model';
import { IExam } from 'app/entities/exam/exam.model';
import { IExamCenter } from 'app/entities/exam-center/exam-center.model';
import { Gender } from 'app/entities/enumerations/gender.model';
import { BloodGroup } from 'app/entities/enumerations/blood-group.model';
import { IdentityType } from 'app/entities/enumerations/identity-type.model';

export interface IExamApplicationForm {
  id?: number;
  regNumber?: string | null;
  firstName?: string;
  middleName?: string | null;
  lastName?: string;
  dob?: dayjs.Dayjs | null;
  fatherName?: string | null;
  email?: string;
  mobileNumber?: string;
  nationality?: string;
  gender?: Gender | null;
  religion?: string | null;
  adharNumber?: string;
  bloodGroup?: BloodGroup | null;
  isApproved?: boolean | null;
  catagory?: string | null;
  identityType?: IdentityType;
  identityNumber?: string;
  imageContentType?: string | null;
  image?: string | null;
  signatureContentType?: string | null;
  signature?: string | null;
  adharContentType?: string | null;
  adhar?: string | null;
  imagePath?: string | null;
  signPath?: string | null;
  adharPath?: string | null;
  correspondingAddress?: IAddress | null;
  student?: IStudent | null;
  exam?: IExam | null;
  examCenter?: IExamCenter | null;
}

export class ExamApplicationForm implements IExamApplicationForm {
  constructor(
    public id?: number,
    public regNumber?: string | null,
    public firstName?: string,
    public middleName?: string | null,
    public lastName?: string,
    public dob?: dayjs.Dayjs | null,
    public fatherName?: string | null,
    public email?: string,
    public mobileNumber?: string,
    public nationality?: string,
    public gender?: Gender | null,
    public religion?: string | null,
    public adharNumber?: string,
    public bloodGroup?: BloodGroup | null,
    public isApproved?: boolean | null,
    public catagory?: string | null,
    public identityType?: IdentityType,
    public identityNumber?: string,
    public imageContentType?: string | null,
    public image?: string | null,
    public signatureContentType?: string | null,
    public signature?: string | null,
    public adharContentType?: string | null,
    public adhar?: string | null,
    public imagePath?: string | null,
    public signPath?: string | null,
    public adharPath?: string | null,
    public correspondingAddress?: IAddress | null,
    public student?: IStudent | null,
    public exam?: IExam | null,
    public examCenter?: IExamCenter | null
  ) {
    this.isApproved = this.isApproved ?? false;
  }
}

export function getExamApplicationFormIdentifier(examApplicationForm: IExamApplicationForm): number | undefined {
  return examApplicationForm.id;
}

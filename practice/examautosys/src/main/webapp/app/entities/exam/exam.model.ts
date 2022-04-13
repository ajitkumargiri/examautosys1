import dayjs from 'dayjs/esm';
import { IAddress } from 'app/entities/address/address.model';
import { IExamApplicationForm } from 'app/entities/exam-application-form/exam-application-form.model';
import { IExamCenter } from 'app/entities/exam-center/exam-center.model';
import { ISession } from 'app/entities/session/session.model';
import { ExamType } from 'app/entities/enumerations/exam-type.model';

export interface IExam {
  id?: number;
  name?: string;
  type?: ExamType;
  code?: string | null;
  date?: dayjs.Dayjs | null;
  startTime?: dayjs.Dayjs | null;
  endTime?: dayjs.Dayjs | null;
  correspondingAddress?: IAddress | null;
  permanentAddress?: IAddress | null;
  applicationForms?: IExamApplicationForm[] | null;
  examCenters?: IExamCenter[] | null;
  session?: ISession | null;
}

export class Exam implements IExam {
  constructor(
    public id?: number,
    public name?: string,
    public type?: ExamType,
    public code?: string | null,
    public date?: dayjs.Dayjs | null,
    public startTime?: dayjs.Dayjs | null,
    public endTime?: dayjs.Dayjs | null,
    public correspondingAddress?: IAddress | null,
    public permanentAddress?: IAddress | null,
    public applicationForms?: IExamApplicationForm[] | null,
    public examCenters?: IExamCenter[] | null,
    public session?: ISession | null
  ) {}
}

export function getExamIdentifier(exam: IExam): number | undefined {
  return exam.id;
}

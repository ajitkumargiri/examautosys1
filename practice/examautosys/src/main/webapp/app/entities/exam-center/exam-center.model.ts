import { IAddress } from 'app/entities/address/address.model';
import { IExamApplicationForm } from 'app/entities/exam-application-form/exam-application-form.model';
import { IExam } from 'app/entities/exam/exam.model';

export interface IExamCenter {
  id?: number;
  name?: string;
  code?: string | null;
  address?: IAddress | null;
  examApplicationForms?: IExamApplicationForm[] | null;
  exam?: IExam | null;
}

export class ExamCenter implements IExamCenter {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string | null,
    public address?: IAddress | null,
    public examApplicationForms?: IExamApplicationForm[] | null,
    public exam?: IExam | null
  ) {}
}

export function getExamCenterIdentifier(examCenter: IExamCenter): number | undefined {
  return examCenter.id;
}

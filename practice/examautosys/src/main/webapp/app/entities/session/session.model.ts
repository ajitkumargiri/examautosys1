import dayjs from 'dayjs/esm';
import { IExam } from 'app/entities/exam/exam.model';
import { ISubjectPaper } from 'app/entities/subject-paper/subject-paper.model';
import { IAcademicBatch } from 'app/entities/academic-batch/academic-batch.model';

export interface ISession {
  id?: number;
  name?: string;
  code?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  exams?: IExam[] | null;
  subjectPapers?: ISubjectPaper[] | null;
  academicBatch?: IAcademicBatch | null;
}

export class Session implements ISession {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string | null,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public exams?: IExam[] | null,
    public subjectPapers?: ISubjectPaper[] | null,
    public academicBatch?: IAcademicBatch | null
  ) {}
}

export function getSessionIdentifier(session: ISession): number | undefined {
  return session.id;
}

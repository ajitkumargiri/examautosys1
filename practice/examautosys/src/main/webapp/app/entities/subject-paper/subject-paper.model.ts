import { ISession } from 'app/entities/session/session.model';
import { SubjectPaperType } from 'app/entities/enumerations/subject-paper-type.model';

export interface ISubjectPaper {
  id?: number;
  name?: string;
  code?: string | null;
  type?: SubjectPaperType | null;
  fullMark?: number | null;
  passMark?: number | null;
  session?: ISession | null;
}

export class SubjectPaper implements ISubjectPaper {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string | null,
    public type?: SubjectPaperType | null,
    public fullMark?: number | null,
    public passMark?: number | null,
    public session?: ISession | null
  ) {}
}

export function getSubjectPaperIdentifier(subjectPaper: ISubjectPaper): number | undefined {
  return subjectPaper.id;
}

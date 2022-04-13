import { ISession } from 'app/entities/session/session.model';
import { IStudent } from 'app/entities/student/student.model';
import { IBranch } from 'app/entities/branch/branch.model';

export interface IAcademicBatch {
  id?: number;
  name?: string;
  code?: string | null;
  academicStartYear?: number;
  academicEndYear?: number;
  sessions?: ISession[] | null;
  students?: IStudent[] | null;
  branch?: IBranch | null;
}

export class AcademicBatch implements IAcademicBatch {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string | null,
    public academicStartYear?: number,
    public academicEndYear?: number,
    public sessions?: ISession[] | null,
    public students?: IStudent[] | null,
    public branch?: IBranch | null
  ) {}
}

export function getAcademicBatchIdentifier(academicBatch: IAcademicBatch): number | undefined {
  return academicBatch.id;
}

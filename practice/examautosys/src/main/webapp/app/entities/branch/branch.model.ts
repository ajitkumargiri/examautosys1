import { IAcademicBatch } from 'app/entities/academic-batch/academic-batch.model';
import { ICourse } from 'app/entities/course/course.model';

export interface IBranch {
  id?: number;
  name?: string | null;
  code?: string | null;
  academicBatches?: IAcademicBatch[] | null;
  course?: ICourse | null;
}

export class Branch implements IBranch {
  constructor(
    public id?: number,
    public name?: string | null,
    public code?: string | null,
    public academicBatches?: IAcademicBatch[] | null,
    public course?: ICourse | null
  ) {}
}

export function getBranchIdentifier(branch: IBranch): number | undefined {
  return branch.id;
}

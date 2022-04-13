import { IBranch } from 'app/entities/branch/branch.model';
import { ICollege } from 'app/entities/college/college.model';

export interface ICourse {
  id?: number;
  name?: string;
  code?: string | null;
  detals?: string | null;
  branches?: IBranch[] | null;
  college?: ICollege | null;
}

export class Course implements ICourse {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string | null,
    public detals?: string | null,
    public branches?: IBranch[] | null,
    public college?: ICollege | null
  ) {}
}

export function getCourseIdentifier(course: ICourse): number | undefined {
  return course.id;
}

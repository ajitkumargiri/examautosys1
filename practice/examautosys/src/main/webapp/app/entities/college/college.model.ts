import { IAddress } from 'app/entities/address/address.model';
import { ICourse } from 'app/entities/course/course.model';
import { IUniversity } from 'app/entities/university/university.model';

export interface ICollege {
  id?: number;
  name?: string;
  code?: string | null;
  address?: IAddress | null;
  courses?: ICourse[] | null;
  university?: IUniversity | null;
}

export class College implements ICollege {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string | null,
    public address?: IAddress | null,
    public courses?: ICourse[] | null,
    public university?: IUniversity | null
  ) {}
}

export function getCollegeIdentifier(college: ICollege): number | undefined {
  return college.id;
}

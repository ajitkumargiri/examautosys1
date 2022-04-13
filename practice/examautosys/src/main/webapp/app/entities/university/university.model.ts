import { IAddress } from 'app/entities/address/address.model';
import { ICollege } from 'app/entities/college/college.model';

export interface IUniversity {
  id?: number;
  name?: string;
  code?: string | null;
  address?: IAddress | null;
  colleges?: ICollege[] | null;
}

export class University implements IUniversity {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string | null,
    public address?: IAddress | null,
    public colleges?: ICollege[] | null
  ) {}
}

export function getUniversityIdentifier(university: IUniversity): number | undefined {
  return university.id;
}

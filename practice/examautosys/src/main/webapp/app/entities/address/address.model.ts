import { State } from 'app/entities/enumerations/state.model';

export interface IAddress {
  id?: number;
  addressLine1?: string;
  addressLine2?: string | null;
  po?: string | null;
  ps?: string | null;
  city?: string;
  state?: State;
  pin?: string;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public addressLine1?: string,
    public addressLine2?: string | null,
    public po?: string | null,
    public ps?: string | null,
    public city?: string,
    public state?: State,
    public pin?: string
  ) {}
}

export function getAddressIdentifier(address: IAddress): number | undefined {
  return address.id;
}

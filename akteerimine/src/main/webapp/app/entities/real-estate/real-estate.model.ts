import { IContract } from 'app/entities/contract/contract.model';

export interface IRealEstate {
  id: number;
  name?: string | null;
  contracts?: Pick<IContract, 'id'>[] | null;
}

export type NewRealEstate = Omit<IRealEstate, 'id'> & { id: null };

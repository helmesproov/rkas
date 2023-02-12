import { IContract } from 'app/entities/contract/contract.model';

export interface IAct {
  id: number;
  name?: string | null;
  status?: string | null;
  editPending?: boolean | null;
  contracts?: Pick<IContract, 'id'>[] | null;
}

export type NewAct = Omit<IAct, 'id'> & { id: null };

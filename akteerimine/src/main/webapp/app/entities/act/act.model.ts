export interface IAct {
  id: number;
  name?: string | null;
  status?: string | null;
  editPending?: boolean | null;
  contracts?: Pick<IContract, 'id'>[] | null;
}

interface IContract {
  id: number;
  name?: string | null;
  acts?: Pick<IAct, 'id'>[] | null;
  realEstates?: Pick<IRealEstate, 'id'>[] | null;
}

interface IRealEstate {
  id: number;
  name?: string | null;
  contracts?: Pick<IContract, 'id'>[] | null;
}

export type NewAct = Omit<IAct, 'id'> & { id: null };

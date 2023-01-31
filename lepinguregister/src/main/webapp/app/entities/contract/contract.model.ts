import { IAct } from 'app/entities/act/act.model';
import { IRealEstate } from 'app/entities/real-estate/real-estate.model';

export interface IContract {
  id: number;
  name?: string | null;
  acts?: Pick<IAct, 'id'>[] | null;
  realEstates?: Pick<IRealEstate, 'id'>[] | null;
}

export type NewContract = Omit<IContract, 'id'> & { id: null };

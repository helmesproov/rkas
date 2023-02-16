import dayjs from 'dayjs/esm';
import { IAct } from "../act/act.model";

export interface IService {
  id: number;
  name?: string | null;
  price?: number | null;
  validFrom?: dayjs.Dayjs | null;
  validTo?: dayjs.Dayjs | null;
  isEdit?: boolean | null;
  actId: number;
  realEstate?: Pick<IRealEstate, 'id'> | null;
  editPending?: boolean | null;
}

interface IRealEstate {
  id: number;
  name?: string | null;
  contracts?: Pick<IContract, 'id'>[] | null;
}

interface IContract {
  id: number;
  name?: string | null;
  acts?: Pick<IAct, 'id'>[] | null;
  realEstates?: Pick<IRealEstate, 'id'>[] | null;
}

export type NewService = Omit<IService, 'id'> & { id: null };

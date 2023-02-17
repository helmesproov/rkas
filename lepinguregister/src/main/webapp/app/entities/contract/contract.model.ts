import { IAct } from 'app/entities/act/act.model';
import { IRealEstate } from 'app/entities/real-estate/real-estate.model';
import dayjs from 'dayjs/esm';

export interface IContract {
  id: number;
  name?: string | null;
  number?: string | null;
  acts?: Pick<IAct, 'id'>[] | null;
  realEstates?: Pick<IRealEstate, 'id'>[] | null;
}

export interface IContractChange {
  id: number;
  serviceId: number;
  realEstateId: number;
  pendingServiceId: number;
  contractNumber?: string | null;
  realEstateName?: string | null;
  serviceName?: string | null;
  price?: number | null;
  validFrom?: dayjs.Dayjs | null;
  validTo?: dayjs.Dayjs | null;
  pendingValidFrom?: dayjs.Dayjs | null;
  pendingValidTo?: dayjs.Dayjs | null;
  pendingPrice?: number | null;
}

export type NewContract = Omit<IContract, 'id'> & { id: null };
export type NewContractChange = Omit<IContractChange, 'id'> & { id: null };

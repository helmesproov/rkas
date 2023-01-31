import dayjs from 'dayjs/esm';
import { IRealEstate } from 'app/entities/real-estate/real-estate.model';

export interface IService {
  id: number;
  name?: string | null;
  price?: number | null;
  validFrom?: dayjs.Dayjs | null;
  validTo?: dayjs.Dayjs | null;
  realEstate?: Pick<IRealEstate, 'id'> | null;
}

export type NewService = Omit<IService, 'id'> & { id: null };

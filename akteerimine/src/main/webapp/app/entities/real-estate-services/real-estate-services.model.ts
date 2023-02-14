
export interface IRealEstateService {
  id: number;
  name?: string | null;
  serviceCount?: number;
}

export type NewRealEstateService = Omit<IRealEstateService, 'id'> & { id: null };

export class IRealEstate {
}
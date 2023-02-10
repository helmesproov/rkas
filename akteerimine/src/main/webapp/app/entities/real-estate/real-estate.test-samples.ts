import { IRealEstate, NewRealEstate } from '../real-estate-services/real-estate-services.model';

export const sampleWithRequiredData: IRealEstate = {
  id: 5942,
};

export const sampleWithPartialData: IRealEstate = {
  id: 72043,
  name: 'global',
};

export const sampleWithFullData: IRealEstate = {
  id: 24585,
  name: 'Franc',
};

export const sampleWithNewData: NewRealEstate = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

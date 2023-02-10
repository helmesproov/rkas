import { IContract, NewContract } from './contract.model';

export const sampleWithRequiredData: IContract = {
  id: 90219,
};

export const sampleWithPartialData: IContract = {
  id: 7385,
  name: 'Reactive coherent Awesome',
};

export const sampleWithFullData: IContract = {
  id: 97473,
  name: 'Refined',
};

export const sampleWithNewData: NewContract = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

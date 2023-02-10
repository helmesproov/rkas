import { IAct, NewAct } from './act.model';

export const sampleWithRequiredData: IAct = {
  id: 13909,
  status: 'Savings navigate',
};

export const sampleWithPartialData: IAct = {
  id: 17667,
  name: 'Dong Heights',
  status: 'Loan Cape',
};

export const sampleWithFullData: IAct = {
  id: 34545,
  name: 'mint User-centric',
  status: 'Orchestrator',
};

export const sampleWithNewData: NewAct = {
  status: 'Argentina',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

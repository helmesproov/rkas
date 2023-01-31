import dayjs from 'dayjs/esm';

import { IService, NewService } from './service.model';

export const sampleWithRequiredData: IService = {
  id: 12311,
};

export const sampleWithPartialData: IService = {
  id: 27136,
  price: 8604,
  validFrom: dayjs('2023-01-30T07:13'),
};

export const sampleWithFullData: IService = {
  id: 69220,
  name: 'Plastic initiatives',
  price: 74754,
  validFrom: dayjs('2023-01-30T02:03'),
  validTo: dayjs('2023-01-29T21:57'),
};

export const sampleWithNewData: NewService = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

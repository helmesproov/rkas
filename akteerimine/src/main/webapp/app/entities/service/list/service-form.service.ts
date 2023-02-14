import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators, AbstractControl, ValidationErrors } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IService, NewService } from '../service.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IService for edit and NewServiceFormGroupInput for create.
 */
type ServiceFormGroupInput = IService | PartialWithRequiredKeyOf<NewService>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IService | NewService> = Omit<T, 'validFrom' | 'validTo'> & {
  validFrom?: string | null;
  validTo?: string | null;
};

type ServiceFormRawValue = FormValueOf<IService>;

type NewServiceFormRawValue = FormValueOf<NewService>;

type ServiceFormDefaults = Pick<NewService, 'id' | 'validFrom' | 'validTo'>;

type ServiceFormGroupContent = {
  id: FormControl<ServiceFormRawValue['id'] | NewService['id']>;
  name: FormControl<ServiceFormRawValue['name']>;
  price: FormControl<ServiceFormRawValue['price']>;
  validFrom: FormControl<ServiceFormRawValue['validFrom']>;
  validTo: FormControl<ServiceFormRawValue['validTo']>;
  realEstate: FormControl<ServiceFormRawValue['realEstate']>;
};

export type ServiceFormGroup = FormGroup<ServiceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServiceFormService {
  createServiceFormGroup(service: ServiceFormGroupInput = { id: null }): ServiceFormGroup {
    const serviceRawValue = this.convertServiceToServiceRawValue({
      ...this.getFormDefaults(),
      ...service,
    });
    return new FormGroup<ServiceFormGroupContent>({
      id: new FormControl(
        { value: serviceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(serviceRawValue.name),
      price: new FormControl(serviceRawValue.price),
      validFrom: new FormControl(serviceRawValue.validFrom),
      validTo: new FormControl(serviceRawValue.validTo),
      realEstate: new FormControl(serviceRawValue.realEstate),
    }, [this.dateValidator]);
  }

  dateValidator(group: AbstractControl): ValidationErrors | null {
    const fromCtrl = group.get('validFrom');
    const toCtrl = group.get('validTo');
    if (fromCtrl == undefined) {
      return { message: 'Algus kuupäev ei tohi olla tühi' }
    }
    if (toCtrl == undefined) {
      return null;
    }
    return new Date(fromCtrl.value) > new Date(toCtrl.value) ? { message: 'Alguse aeg ei tohi enne lõpu aega' } : null;
  }

  getService(form: ServiceFormGroup): IService {
    return this.convertServiceRawValueToService(form.getRawValue() as ServiceFormRawValue);
  }

  resetForm(form: ServiceFormGroup, service: ServiceFormGroupInput): void {
    const serviceRawValue = this.convertServiceToServiceRawValue({ ...this.getFormDefaults(), ...service });
    form.reset(
      {
        ...serviceRawValue,
        id: { value: serviceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ServiceFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      validFrom: currentTime,
      validTo: currentTime,
    };
  }

  private convertServiceRawValueToService(rawService: ServiceFormRawValue): IService {
    return {
      ...rawService,
      validFrom: dayjs(rawService.validFrom, DATE_TIME_FORMAT),
      validTo: dayjs(rawService.validTo, DATE_TIME_FORMAT),
    };
  }

  private convertServiceToServiceRawValue(
    service: IService | (Partial<NewService> & ServiceFormDefaults)
  ): ServiceFormRawValue | PartialWithRequiredKeyOf<NewServiceFormRawValue> {
    return {
      ...service,
      validFrom: service.validFrom ? service.validFrom.format(DATE_TIME_FORMAT) : undefined,
      validTo: service.validTo ? service.validTo.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}

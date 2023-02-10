import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRealEstate, NewRealEstate } from '../../real-estate-services/real-estate-services.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRealEstate for edit and NewRealEstateFormGroupInput for create.
 */
type RealEstateFormGroupInput = IRealEstate | PartialWithRequiredKeyOf<NewRealEstate>;

type RealEstateFormDefaults = Pick<NewRealEstate, 'id' | 'contracts'>;

type RealEstateFormGroupContent = {
  id: FormControl<IRealEstate['id'] | NewRealEstate['id']>;
  name: FormControl<IRealEstate['name']>;
  contracts: FormControl<IRealEstate['contracts']>;
};

export type RealEstateFormGroup = FormGroup<RealEstateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RealEstateFormService {
  createRealEstateFormGroup(realEstate: RealEstateFormGroupInput = { id: null }): RealEstateFormGroup {
    const realEstateRawValue = {
      ...this.getFormDefaults(),
      ...realEstate,
    };
    return new FormGroup<RealEstateFormGroupContent>({
      id: new FormControl(
        { value: realEstateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(realEstateRawValue.name),
      contracts: new FormControl(realEstateRawValue.contracts ?? []),
    });
  }

  getRealEstate(form: RealEstateFormGroup): IRealEstate | NewRealEstate {
    return form.getRawValue() as IRealEstate | NewRealEstate;
  }

  resetForm(form: RealEstateFormGroup, realEstate: RealEstateFormGroupInput): void {
    const realEstateRawValue = { ...this.getFormDefaults(), ...realEstate };
    form.reset(
      {
        ...realEstateRawValue,
        id: { value: realEstateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RealEstateFormDefaults {
    return {
      id: null,
      contracts: [],
    };
  }
}

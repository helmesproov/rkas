import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAct, NewAct } from '../act.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAct for edit and NewActFormGroupInput for create.
 */
type ActFormGroupInput = IAct | PartialWithRequiredKeyOf<NewAct>;

type ActFormDefaults = Pick<NewAct, 'id' | 'contracts'>;

type ActFormGroupContent = {
  id: FormControl<IAct['id'] | NewAct['id']>;
  name: FormControl<IAct['name']>;
  status: FormControl<IAct['status']>;
  contracts: FormControl<IAct['contracts']>;
};

export type ActFormGroup = FormGroup<ActFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ActFormService {
  createActFormGroup(act: ActFormGroupInput = { id: null }): ActFormGroup {
    const actRawValue = {
      ...this.getFormDefaults(),
      ...act,
    };
    return new FormGroup<ActFormGroupContent>({
      id: new FormControl(
        { value: actRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(actRawValue.name),
      status: new FormControl(actRawValue.status, {
        validators: [Validators.required],
      }),
      contracts: new FormControl(actRawValue.contracts ?? []),
    });
  }

  getAct(form: ActFormGroup): IAct | NewAct {
    return form.getRawValue() as IAct | NewAct;
  }

  resetForm(form: ActFormGroup, act: ActFormGroupInput): void {
    const actRawValue = { ...this.getFormDefaults(), ...act };
    form.reset(
      {
        ...actRawValue,
        id: { value: actRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ActFormDefaults {
    return {
      id: null,
      contracts: [],
    };
  }
}

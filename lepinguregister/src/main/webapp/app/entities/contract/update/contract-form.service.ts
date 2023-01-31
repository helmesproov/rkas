import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IContract, NewContract } from '../contract.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContract for edit and NewContractFormGroupInput for create.
 */
type ContractFormGroupInput = IContract | PartialWithRequiredKeyOf<NewContract>;

type ContractFormDefaults = Pick<NewContract, 'id' | 'acts' | 'realEstates'>;

type ContractFormGroupContent = {
  id: FormControl<IContract['id'] | NewContract['id']>;
  name: FormControl<IContract['name']>;
  acts: FormControl<IContract['acts']>;
  realEstates: FormControl<IContract['realEstates']>;
};

export type ContractFormGroup = FormGroup<ContractFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContractFormService {
  createContractFormGroup(contract: ContractFormGroupInput = { id: null }): ContractFormGroup {
    const contractRawValue = {
      ...this.getFormDefaults(),
      ...contract,
    };
    return new FormGroup<ContractFormGroupContent>({
      id: new FormControl(
        { value: contractRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(contractRawValue.name),
      acts: new FormControl(contractRawValue.acts ?? []),
      realEstates: new FormControl(contractRawValue.realEstates ?? []),
    });
  }

  getContract(form: ContractFormGroup): IContract | NewContract {
    return form.getRawValue() as IContract | NewContract;
  }

  resetForm(form: ContractFormGroup, contract: ContractFormGroupInput): void {
    const contractRawValue = { ...this.getFormDefaults(), ...contract };
    form.reset(
      {
        ...contractRawValue,
        id: { value: contractRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ContractFormDefaults {
    return {
      id: null,
      acts: [],
      realEstates: [],
    };
  }
}

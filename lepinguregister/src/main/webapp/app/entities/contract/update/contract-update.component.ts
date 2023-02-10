import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ContractFormService, ContractFormGroup } from './contract-form.service';
import { IContract } from '../contract.model';
import { ContractService } from '../service/contract.service';
import { IAct } from 'app/entities/act/act.model';
import { ActService } from 'app/entities/act/service/act.service';

@Component({
  selector: 'jhi-contract-update',
  templateUrl: './contract-update.component.html',
})
export class ContractUpdateComponent implements OnInit {
  isSaving = false;
  contract: IContract | null = null;

  actsSharedCollection: IAct[] = [];

  editForm: ContractFormGroup = this.contractFormService.createContractFormGroup();

  constructor(
    protected contractService: ContractService,
    protected contractFormService: ContractFormService,
    protected actService: ActService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAct = (o1: IAct | null, o2: IAct | null): boolean => this.actService.compareAct(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contract }) => {
      this.contract = contract;
      if (contract) {
        this.updateForm(contract);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contract = this.contractFormService.getContract(this.editForm);
    if (contract.id == null) {
      this.subscribeToSaveResponse(this.contractService.create(contract));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContract>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(contract: IContract): void {
    this.contract = contract;
    this.contractFormService.resetForm(this.editForm, contract);

    this.actsSharedCollection = this.actService.addActToCollectionIfMissing<IAct>(this.actsSharedCollection, ...(contract.acts ?? []));
  }

  protected loadRelationshipsOptions(): void {
    this.actService
      .query()
      .pipe(map((res: HttpResponse<IAct[]>) => res.body ?? []))
      .pipe(map((acts: IAct[]) => this.actService.addActToCollectionIfMissing<IAct>(acts, ...(this.contract?.acts ?? []))))
      .subscribe((acts: IAct[]) => (this.actsSharedCollection = acts));
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { RealEstateFormService, RealEstateFormGroup } from './real-estate-form.service';
import { IRealEstate } from '../real-estate.model';
import { RealEstateService } from '../service/real-estate.service';
import { IContract } from 'app/entities/contract/contract.model';
import { ContractService } from 'app/entities/contract/service/contract.service';

@Component({
  selector: 'jhi-real-estate-update',
  templateUrl: './real-estate-update.component.html',
})
export class RealEstateUpdateComponent implements OnInit {
  isSaving = false;
  realEstate: IRealEstate | null = null;

  contractsSharedCollection: IContract[] = [];

  editForm: RealEstateFormGroup = this.realEstateFormService.createRealEstateFormGroup();

  constructor(
    protected realEstateService: RealEstateService,
    protected realEstateFormService: RealEstateFormService,
    protected contractService: ContractService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareContract = (o1: IContract | null, o2: IContract | null): boolean => this.contractService.compareContract(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ realEstate }) => {
      this.realEstate = realEstate;
      if (realEstate) {
        this.updateForm(realEstate);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const realEstate = this.realEstateFormService.getRealEstate(this.editForm);
    if (realEstate.id !== null) {
      this.subscribeToSaveResponse(this.realEstateService.update(realEstate));
    } else {
      this.subscribeToSaveResponse(this.realEstateService.create(realEstate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRealEstate>>): void {
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

  protected updateForm(realEstate: IRealEstate): void {
    this.realEstate = realEstate;
    this.realEstateFormService.resetForm(this.editForm, realEstate);

    this.contractsSharedCollection = this.contractService.addContractToCollectionIfMissing<IContract>(
      this.contractsSharedCollection,
      ...(realEstate.contracts ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contractService
      .query()
      .pipe(map((res: HttpResponse<IContract[]>) => res.body ?? []))
      .pipe(
        map((contracts: IContract[]) =>
          this.contractService.addContractToCollectionIfMissing<IContract>(contracts, ...(this.realEstate?.contracts ?? []))
        )
      )
      .subscribe((contracts: IContract[]) => (this.contractsSharedCollection = contracts));
  }
}

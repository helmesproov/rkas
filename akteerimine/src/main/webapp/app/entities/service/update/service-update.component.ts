import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ServiceFormService, ServiceFormGroup } from './service-form.service';
import { IService } from '../service.model';
import { ServiceService } from '../service/service.service';
import { IRealEstate } from 'app/entities/real-estate/real-estate.model';
import { RealEstateService } from 'app/entities/real-estate/service/real-estate.service';

@Component({
  selector: 'jhi-service-update',
  templateUrl: './service-update.component.html',
})
export class ServiceUpdateComponent implements OnInit {
  isSaving = false;
  service: IService | null = null;

  realEstatesSharedCollection: IRealEstate[] = [];

  editForm: ServiceFormGroup = this.serviceFormService.createServiceFormGroup();

  constructor(
    protected serviceService: ServiceService,
    protected serviceFormService: ServiceFormService,
    protected realEstateService: RealEstateService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareRealEstate = (o1: IRealEstate | null, o2: IRealEstate | null): boolean => this.realEstateService.compareRealEstate(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ service }) => {
      this.service = service;
      if (service) {
        this.updateForm(service);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const service = this.serviceFormService.getService(this.editForm);
    if (service.id !== null) {
      this.subscribeToSaveResponse(this.serviceService.update(service));
    } else {
      this.subscribeToSaveResponse(this.serviceService.create(service));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IService>>): void {
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

  protected updateForm(service: IService): void {
    this.service = service;
    this.serviceFormService.resetForm(this.editForm, service);

    this.realEstatesSharedCollection = this.realEstateService.addRealEstateToCollectionIfMissing<IRealEstate>(
      this.realEstatesSharedCollection,
      service.realEstate
    );
  }

  protected loadRelationshipsOptions(): void {
    this.realEstateService
      .query()
      .pipe(map((res: HttpResponse<IRealEstate[]>) => res.body ?? []))
      .pipe(
        map((realEstates: IRealEstate[]) =>
          this.realEstateService.addRealEstateToCollectionIfMissing<IRealEstate>(realEstates, this.service?.realEstate)
        )
      )
      .subscribe((realEstates: IRealEstate[]) => (this.realEstatesSharedCollection = realEstates));
  }
}

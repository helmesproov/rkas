import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest, Observable, switchMap, tap, finalize } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

import { IService } from '../service.model';
import { EntityArrayResponseType, ServiceService } from '../service/service.service';
import { ServiceFormGroup, ServiceFormService } from "./service-form.service";

@Component({
    selector: 'jhi-service',
    templateUrl: './service.component.html',
})
export class ServiceComponent implements OnInit {
    @Input() realEstateId!: number;
    @Input() actId!: number;
    services?: IService[];
    editedService?: IService;
    isLoading = false;
    isSaving = false;

    constructor(
        protected serviceService: ServiceService,
        protected activatedRoute: ActivatedRoute,
        public router: Router,
        protected serviceFormService: ServiceFormService,
    ) {
    }

    editForm: ServiceFormGroup = this.serviceFormService.createServiceFormGroup();

    trackId = (_index: number, item: IService): number => this.serviceService.getServiceIdentifier(item);

    ngOnInit(): void {
        this.load();
    }

    load(): void {
        this.loadFromBackendWithRouteInformations().subscribe({
            next: (res: EntityArrayResponseType) => {
                this.onResponseSuccess(res);
            },
        });
    }

    protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
        return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
            switchMap(() => this.queryBackend())
        );
    }

    protected onResponseSuccess(response: EntityArrayResponseType): void {
        this.services = this.fillComponentAttributesFromResponseBody(response.body);
        this.services = this.services.map(service => ({ ...service, isEdit: false, actId: this.actId }))
    }

    protected fillComponentAttributesFromResponseBody(data: IService[] | null): IService[] {
        return data ?? [];
    }

    protected queryBackend(): Observable<EntityArrayResponseType> {
        this.isLoading = true;
        return this.serviceService.query(this.realEstateId).pipe(tap(() => (this.isLoading = false)));
    }

    protected updateForm(service: IService): void {
        this.serviceFormService.resetForm(this.editForm, service);
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IService>>): void {
        result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
            next: () => this.onSaveSuccess(),
            error: () => this.onSaveError(),
        });
    }

    protected onSaveSuccess(): void {
        this.load();
    }

    protected onSaveFinalize(): void {
        this.isSaving = false;
    }

    protected onSaveError(): void {
    }

    onEdit(service: IService) {
        this.services?.forEach(service => {
            service.isEdit = false;
        });
        service.isEdit = true;
        if (service) {
            this.updateForm(service);
            this.editedService = service;
        }
    }

    update() {
        if (this.editedService) {
            this.editedService.isEdit = false;
        }
        const service = this.serviceFormService.getService(this.editForm);
        service.actId = this.actId;
        this.subscribeToSaveResponse(this.serviceService.update(service));
    }
}

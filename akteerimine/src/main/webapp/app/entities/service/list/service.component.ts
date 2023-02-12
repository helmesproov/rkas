import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest, Observable, switchMap, tap } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IService } from '../service.model';
import { EntityArrayResponseType, ServiceService } from '../service/service.service';

@Component({
    selector: 'jhi-service',
    templateUrl: './service.component.html',
})
export class ServiceComponent implements OnInit {
    @Input() realEstateId!: number;
    @Input() actId!: number;
    services?: IService[];
    isLoading = false;

    constructor(
        protected serviceService: ServiceService,
        protected activatedRoute: ActivatedRoute,
        public router: Router,
        protected activeModal: NgbActiveModal
    ) {
    }

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

    onEdit(service: IService) {
        this.services?.forEach(service => {
            service.isEdit = false;
        });
        service.isEdit = true;
    }

    update(service: IService) {
        service.isEdit = false;
        this.serviceService.update(service).subscribe(() => {
            this.activeModal.close();
            this.router.navigate(['/act']);
        });
    }

}

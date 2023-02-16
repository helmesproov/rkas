import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IRealEstateService } from "../real-estate-services.model";
import { RealEstateServicesService } from "../service/real-estate-services.service";
import { EntityArrayResponseType } from "../service/real-estate-services.service";
import { combineLatest, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RealEstateServiceModifyDialogComponent } from "../update/real-estate-service-modify-dialog.component";

@Component({
    selector: 'real-estate-services',
    templateUrl: './real-estate-services.component.html',
})
export class RealEstateServicesComponent implements OnInit {
    @Input() actId!: number;
    realEstateServices?: IRealEstateService[];
    isLoading = false;

    constructor(
        protected realEstateServicesService: RealEstateServicesService,
        protected activatedRoute: ActivatedRoute,
        public router: Router,
        protected modalService: NgbModal
    ) {
    }

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

    modify(realEstate: IRealEstateService): void {
        const modalRef = this.modalService.open(RealEstateServiceModifyDialogComponent, {
            size: 'xl',
            backdrop: 'static'
        });
        modalRef.componentInstance.realEstate = realEstate;
        modalRef.componentInstance.actId = this.actId;
    }

    protected onResponseSuccess(response: EntityArrayResponseType): void {
        this.realEstateServices = this.fillComponentAttributesFromResponseBody(response.body)
    }

    protected fillComponentAttributesFromResponseBody(data: IRealEstateService[] | null): IRealEstateService[] {
        return data ?? [];
    }

    protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
        return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
            switchMap(() => this.queryBackend())
        );
    }

    protected queryBackend(): Observable<EntityArrayResponseType> {
        this.isLoading = true;
        return this.realEstateServicesService.query(this.actId).pipe(tap(() => (this.isLoading = false)));
    }

}

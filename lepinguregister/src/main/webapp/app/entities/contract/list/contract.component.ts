import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest, Observable, switchMap, tap } from 'rxjs';

import { IContractChange } from '../contract.model';
import { ContractService, EntityArrayResponseType } from '../service/contract.service';

@Component({
    selector: 'jhi-contract',
    templateUrl: './contract.component.html',
})
export class ContractComponent implements OnInit {
    contracts?: IContractChange[];
    isLoading = false;

    constructor(
        protected contractService: ContractService,
        protected activatedRoute: ActivatedRoute,
        public router: Router
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

    protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
        return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
            switchMap(() => this.queryBackend())
        );
    }

    protected onResponseSuccess(response: EntityArrayResponseType): void {
        this.contracts = this.fillComponentAttributesFromResponseBody(response.body);
    }

    protected fillComponentAttributesFromResponseBody(data: IContractChange[] | null): IContractChange[] {
        return data ?? [];
    }

    protected queryBackend(): Observable<EntityArrayResponseType> {
        this.isLoading = true;
        return this.contractService.query().pipe(tap(() => (this.isLoading = false)));
    }

    update(contract: IContractChange): void {
        this.contractService.update(contract).subscribe(() => {
            this.load();
        });
    }

}

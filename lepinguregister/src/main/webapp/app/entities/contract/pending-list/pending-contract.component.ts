import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest, Observable, switchMap, tap } from 'rxjs';

import { IContractChange } from '../contract.model';
import { ContractService, ContractChangeArrayResponseType } from '../service/contract.service';

@Component({
    selector: 'pending-contracts',
    templateUrl: './pending-contract.component.html',
})
export class PendingContractComponent implements OnInit {
    contracts?: IContractChange[];
    isLoading = false;
    DATE_TIME_FORMAT = 'YYYY-MM-DDTHH:mm:ss';

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
            next: (res: ContractChangeArrayResponseType) => {
                this.onResponseSuccess(res);
            },
        });
    }

    protected loadFromBackendWithRouteInformations(): Observable<ContractChangeArrayResponseType> {
        return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
            switchMap(() => this.queryBackend())
        );
    }

    protected onResponseSuccess(response: ContractChangeArrayResponseType): void {
        this.contracts = this.fillComponentAttributesFromResponseBody(response.body);
    }

    protected fillComponentAttributesFromResponseBody(data: IContractChange[] | null): IContractChange[] {
        return data ?? [];
    }

    protected queryBackend(): Observable<ContractChangeArrayResponseType> {
        this.isLoading = true;
        return this.contractService.queryPendingChanges().pipe(tap(() => (this.isLoading = false)));
    }

    update(contract: IContractChange): void {
        this.contractService.update(contract).subscribe(() => {
            this.load();
        });
    }

}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Observable, switchMap, tap } from 'rxjs';

import { IContract } from '../contract.model';
import { ASC, DEFAULT_SORT_DATA, DESC, SORT } from 'app/config/navigation.constants';
import { ContractService, EntityArrayResponseType } from '../service/contract.service';
import { SortService } from 'app/shared/sort/sort.service';

@Component({
    selector: 'jhi-contract',
    templateUrl: './contract.component.html',
})
export class ContractComponent implements OnInit {
    contracts?: IContract[];
    isLoading = false;

    predicate = 'id';
    ascending = true;

    constructor(
        protected contractService: ContractService,
        protected activatedRoute: ActivatedRoute,
        public router: Router,
        protected sortService: SortService,
    ) {
    }

    trackId = (_index: number, item: IContract): number => this.contractService.getContractIdentifier(item);

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

    navigateToWithComponentValues(): void {
        this.handleNavigation(this.predicate, this.ascending);
    }

    protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
        return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
            tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
            switchMap(() => this.queryBackend(this.predicate, this.ascending))
        );
    }

    protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
        const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
        this.predicate = sort[0];
        this.ascending = sort[1] === ASC;
    }

    protected onResponseSuccess(response: EntityArrayResponseType): void {
        const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
        this.contracts = this.refineData(dataFromBody);
    }

    protected refineData(data: IContract[]): IContract[] {
        return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
    }

    protected fillComponentAttributesFromResponseBody(data: IContract[] | null): IContract[] {
        return data ?? [];
    }

    protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
        this.isLoading = true;
        const queryObject = {
            eagerload: true,
            sort: this.getSortQueryParam(predicate, ascending),
        };
        return this.contractService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
    }

    protected handleNavigation(predicate?: string, ascending?: boolean): void {
        const queryParamsObj = {
            sort: this.getSortQueryParam(predicate, ascending),
        };

        this.router.navigate(['./'], {
            relativeTo: this.activatedRoute,
            queryParams: queryParamsObj,
        });
    }

    protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
        const ascendingQueryParam = ascending ? ASC : DESC;
        if (predicate === '') {
            return [];
        } else {
            return [predicate + ',' + ascendingQueryParam];
        }
    }
}

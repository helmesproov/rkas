import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import dayjs from 'dayjs/esm';
import { map } from 'rxjs/operators';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { IContract, IContractChange, NewContract, NewContractChange } from '../contract.model';

type RestOf<T extends IContractChange | NewContractChange> = Omit<T, 'validFrom' | 'validTo'> & {
    validFrom?: dayjs.Dayjs | null;
    validTo?: dayjs.Dayjs | null;
};

export type RestService = RestOf<IContractChange>;

export type EntityResponseType = HttpResponse<IContract>;
export type EntityArrayResponseType = HttpResponse<IContractChange[]>;
export type ContractChangeArrayResponseType = HttpResponse<IContractChange[]>;

@Injectable({ providedIn: 'root' })
export class ContractService {
    protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contracts');

    constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {
    }

    create(contract: NewContract): Observable<EntityResponseType> {
        return this.http.post<IContract>(this.resourceUrl, contract, { observe: 'response' });
    }

    update(contract: IContractChange): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contract);
        return this.http
            .put<RestService>(`api/service/pending/${contract.id}`, copy, { observe: 'response' })
            .pipe(map(res => this.convertResponseFromServer(res)));
    }

    protected convertDateFromClient<T extends IContractChange>(contract: T): RestOf<T> {
        return {
            ...contract,
            validFrom: contract.validFrom?.toJSON() ?? null,
            validTo: contract.validTo?.toJSON() ?? null,
        };
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IContract>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(): Observable<ContractChangeArrayResponseType> {
        return this.http.get<IContractChange[]>(`${this.resourceUrl}/pending-changes`, { observe: 'response' })
            .pipe(map(res => this.convertResponseArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<{}>> {
        return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    getContractIdentifier(contract: Pick<IContract, 'id'>): number {
        return contract.id;
    }

    compareContract(o1: Pick<IContract, 'id'> | null, o2: Pick<IContract, 'id'> | null): boolean {
        return o1 && o2 ? this.getContractIdentifier(o1) === this.getContractIdentifier(o2) : o1 === o2;
    }

    addContractToCollectionIfMissing<Type extends Pick<IContract, 'id'>>(
        contractCollection: Type[],
        ...contractsToCheck: (Type | null | undefined)[]
    ): Type[] {
        const contracts: Type[] = contractsToCheck.filter(isPresent);
        if (contracts.length > 0) {
            const contractCollectionIdentifiers = contractCollection.map(contractItem => this.getContractIdentifier(contractItem)!);
            const contractsToAdd = contracts.filter(contractItem => {
                const contractIdentifier = this.getContractIdentifier(contractItem);
                if (contractCollectionIdentifiers.includes(contractIdentifier)) {
                    return false;
                }
                contractCollectionIdentifiers.push(contractIdentifier);
                return true;
            });
            return [...contractsToAdd, ...contractCollection];
        }
        return contractCollection;
    }

    protected convertResponseArrayFromServer(res: HttpResponse<RestService[]>): HttpResponse<IContractChange[]> {
        return res.clone({
            body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
        });
    }

    protected convertResponseFromServer(res: HttpResponse<RestService>): HttpResponse<IContractChange> {
        return res.clone({
            body: res.body ? this.convertDateFromServer(res.body) : null,
        });
    }

    protected convertDateFromServer(restService: RestService): IContractChange {
        return {
            ...restService,
            validFrom: restService.validFrom ? dayjs(restService.validFrom) : undefined,
            validTo: restService.validTo ? dayjs(restService.validTo) : undefined,
        };
    }
}

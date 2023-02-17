import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { IService } from '../service.model';
import { IRealEstateService } from "../../real-estate-services/real-estate-services.model";
import { HttpParams } from '@angular/common/http';



type RestOf<T extends IService> = Omit<T, 'validFrom' | 'validTo'> & {
    validFrom?: dayjs.Dayjs | null;
    validTo?: dayjs.Dayjs | null;
};

export type RestService = RestOf<IService>;

export type EntityResponseType = HttpResponse<IService>;
export type EntityArrayResponseType = HttpResponse<IService[]>;

@Injectable({ providedIn: 'root' })
export class ServiceService {
    protected resourceUrl = this.applicationConfigService.getEndpointFor('api/services');

    constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {
    }

    update(service: IService): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(service);
        return this.http
            .put<RestService>(`${this.resourceUrl}/${this.getServiceIdentifier(service)}`, copy, { observe: 'response' })
            .pipe(map(res => this.convertResponseFromServer(res)));
    }

    query(realEstateId: number): Observable<EntityArrayResponseType> {
        let queryParams = new HttpParams().append("realEstateId", realEstateId);
        return this.http.get<IService[]>(this.resourceUrl, { observe: 'response', params: queryParams })
            .pipe(map(res => this.convertResponseArrayFromServer(res)));
    }

    getServiceIdentifier(service: Pick<IService, 'id'>): number {
        return service.id;
    }

    protected convertDateFromClient<T extends IService>(service: T): RestOf<T> {
        return {
            ...service,
            validFrom: service.validFrom?.toJSON() ?? null,
            validTo: service.validTo?.toJSON() ?? null,
        };
    }

    protected convertDateFromServer(restService: RestService): IService {
        return {
            ...restService,
            validFrom: restService.validFrom ? dayjs(restService.validFrom) : undefined,
            validTo: restService.validTo ? dayjs(restService.validTo) : undefined,
        };
    }

    protected convertResponseFromServer(res: HttpResponse<RestService>): HttpResponse<IService> {
        return res.clone({
            body: res.body ? this.convertDateFromServer(res.body) : null,
        });
    }

    protected convertResponseArrayFromServer(res: HttpResponse<RestService[]>): HttpResponse<IService[]> {
        return res.clone({
            body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
        });
    }
}

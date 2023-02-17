import { Injectable } from "@angular/core";
import { ApplicationConfigService } from "../../../core/config/application-config.service";
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IRealEstateService } from "../real-estate-services.model";
import { HttpParams } from '@angular/common/http';

export type EntityArrayResponseType = HttpResponse<IRealEstateService[]>;

@Injectable({ providedIn: 'root' })
export class RealEstateServicesService {

    protected resourceUrl = this.applicationConfigService.getEndpointFor('api/real-estates');

    constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {
    }

    query(actId: number): Observable<EntityArrayResponseType> {
        let queryParams = new HttpParams().append("actId",actId);
        return this.http.get<IRealEstateService[]>(this.resourceUrl, { observe: 'response', params: queryParams });
    }
}
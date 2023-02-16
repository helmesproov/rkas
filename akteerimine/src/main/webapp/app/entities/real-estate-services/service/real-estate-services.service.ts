import { Injectable } from "@angular/core";
import { ApplicationConfigService } from "../../../core/config/application-config.service";
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IRealEstateService } from "../real-estate-services.model";

export type EntityArrayResponseType = HttpResponse<IRealEstateService[]>;

@Injectable({ providedIn: 'root' })
export class RealEstateServicesService {

    protected resourceUrl = this.applicationConfigService.getEndpointFor('api/real-estates');

    constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {
    }

    query(id: number): Observable<EntityArrayResponseType> {
        return this.http.get<IRealEstateService[]>(`${this.resourceUrl}/act/${id}`, { observe: 'response' });
    }
}
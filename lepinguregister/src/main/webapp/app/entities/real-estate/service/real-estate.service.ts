import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRealEstate, NewRealEstate } from '../real-estate.model';

export type PartialUpdateRealEstate = Partial<IRealEstate> & Pick<IRealEstate, 'id'>;

export type EntityResponseType = HttpResponse<IRealEstate>;
export type EntityArrayResponseType = HttpResponse<IRealEstate[]>;

@Injectable({ providedIn: 'root' })
export class RealEstateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/real-estates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(realEstate: NewRealEstate): Observable<EntityResponseType> {
    return this.http.post<IRealEstate>(this.resourceUrl, realEstate, { observe: 'response' });
  }

  update(realEstate: IRealEstate): Observable<EntityResponseType> {
    return this.http.put<IRealEstate>(`${this.resourceUrl}/${this.getRealEstateIdentifier(realEstate)}`, realEstate, {
      observe: 'response',
    });
  }

  partialUpdate(realEstate: PartialUpdateRealEstate): Observable<EntityResponseType> {
    return this.http.patch<IRealEstate>(`${this.resourceUrl}/${this.getRealEstateIdentifier(realEstate)}`, realEstate, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRealEstate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRealEstate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRealEstateIdentifier(realEstate: Pick<IRealEstate, 'id'>): number {
    return realEstate.id;
  }

  compareRealEstate(o1: Pick<IRealEstate, 'id'> | null, o2: Pick<IRealEstate, 'id'> | null): boolean {
    return o1 && o2 ? this.getRealEstateIdentifier(o1) === this.getRealEstateIdentifier(o2) : o1 === o2;
  }

  addRealEstateToCollectionIfMissing<Type extends Pick<IRealEstate, 'id'>>(
    realEstateCollection: Type[],
    ...realEstatesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const realEstates: Type[] = realEstatesToCheck.filter(isPresent);
    if (realEstates.length > 0) {
      const realEstateCollectionIdentifiers = realEstateCollection.map(realEstateItem => this.getRealEstateIdentifier(realEstateItem)!);
      const realEstatesToAdd = realEstates.filter(realEstateItem => {
        const realEstateIdentifier = this.getRealEstateIdentifier(realEstateItem);
        if (realEstateCollectionIdentifiers.includes(realEstateIdentifier)) {
          return false;
        }
        realEstateCollectionIdentifiers.push(realEstateIdentifier);
        return true;
      });
      return [...realEstatesToAdd, ...realEstateCollection];
    }
    return realEstateCollection;
  }
}

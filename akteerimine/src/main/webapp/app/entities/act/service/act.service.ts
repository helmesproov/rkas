import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAct, NewAct } from '../act.model';

export type PartialUpdateAct = Partial<IAct> & Pick<IAct, 'id'>;

export type EntityResponseType = HttpResponse<IAct>;
export type EntityArrayResponseType = HttpResponse<IAct[]>;

@Injectable({ providedIn: 'root' })
export class ActService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/acts');
  protected realEstatesResourceUrl = this.applicationConfigService.getEndpointFor('api/acts/real-estates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(act: NewAct): Observable<EntityResponseType> {
    return this.http.post<IAct>(this.resourceUrl, act, { observe: 'response' });
  }

  update(act: IAct): Observable<EntityResponseType> {
    return this.http.put<IAct>(`${this.resourceUrl}/${this.getActIdentifier(act)}`, act, { observe: 'response' });
  }

  partialUpdate(act: PartialUpdateAct): Observable<EntityResponseType> {
    return this.http.patch<IAct>(`${this.resourceUrl}/${this.getActIdentifier(act)}`, act, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAct>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    return this.http.get<IAct[]>(this.resourceUrl, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getActIdentifier(act: Pick<IAct, 'id'>): number {
    return act.id;
  }

  compareAct(o1: Pick<IAct, 'id'> | null, o2: Pick<IAct, 'id'> | null): boolean {
    return o1 && o2 ? this.getActIdentifier(o1) === this.getActIdentifier(o2) : o1 === o2;
  }

  addActToCollectionIfMissing<Type extends Pick<IAct, 'id'>>(actCollection: Type[], ...actsToCheck: (Type | null | undefined)[]): Type[] {
    const acts: Type[] = actsToCheck.filter(isPresent);
    if (acts.length > 0) {
      const actCollectionIdentifiers = actCollection.map(actItem => this.getActIdentifier(actItem)!);
      const actsToAdd = acts.filter(actItem => {
        const actIdentifier = this.getActIdentifier(actItem);
        if (actCollectionIdentifiers.includes(actIdentifier)) {
          return false;
        }
        actCollectionIdentifiers.push(actIdentifier);
        return true;
      });
      return [...actsToAdd, ...actCollection];
    }
    return actCollection;
  }
}

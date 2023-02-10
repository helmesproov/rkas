import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRealEstate } from '../../real-estate-services/real-estate-services.model';
import { RealEstateService } from '../service/real-estate.service';

@Injectable({ providedIn: 'root' })
export class RealEstateRoutingResolveService implements Resolve<IRealEstate | null> {
  constructor(protected service: RealEstateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRealEstate | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((realEstate: HttpResponse<IRealEstate>) => {
          if (realEstate.body) {
            return of(realEstate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}

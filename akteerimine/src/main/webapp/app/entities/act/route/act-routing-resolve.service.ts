import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAct } from '../act.model';
import { ActService } from '../service/act.service';

@Injectable({ providedIn: 'root' })
export class ActRoutingResolveService implements Resolve<IAct | null> {
  constructor(protected service: ActService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAct | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((act: HttpResponse<IAct>) => {
          if (act.body) {
            return of(act.body);
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

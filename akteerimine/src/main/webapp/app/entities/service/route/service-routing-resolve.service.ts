import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { Observable, of } from 'rxjs';

import { IService } from '../service.model';
import { ServiceService } from '../service/service.service';

@Injectable({ providedIn: 'root' })
export class ServiceRoutingResolveService implements Resolve<IService | null> {
    constructor(protected service: ServiceService, protected router: Router) {
    }

    resolve(route: ActivatedRouteSnapshot): Observable<IService | null | never> {
        return of(null);
    }
}

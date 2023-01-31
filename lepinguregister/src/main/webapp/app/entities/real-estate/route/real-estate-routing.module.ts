import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RealEstateComponent } from '../list/real-estate.component';
import { RealEstateDetailComponent } from '../detail/real-estate-detail.component';
import { RealEstateUpdateComponent } from '../update/real-estate-update.component';
import { RealEstateRoutingResolveService } from './real-estate-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const realEstateRoute: Routes = [
  {
    path: '',
    component: RealEstateComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RealEstateDetailComponent,
    resolve: {
      realEstate: RealEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RealEstateUpdateComponent,
    resolve: {
      realEstate: RealEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RealEstateUpdateComponent,
    resolve: {
      realEstate: RealEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(realEstateRoute)],
  exports: [RouterModule],
})
export class RealEstateRoutingModule {}

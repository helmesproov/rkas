import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ActComponent } from '../list/act.component';
import { ActDetailComponent } from '../detail/act-detail.component';
import { ActUpdateComponent } from '../update/act-update.component';
import { ActRoutingResolveService } from './act-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const actRoute: Routes = [
  {
    path: '',
    component: ActComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ActDetailComponent,
    resolve: {
      act: ActRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ActUpdateComponent,
    resolve: {
      act: ActRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ActUpdateComponent,
    resolve: {
      act: ActRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(actRoute)],
  exports: [RouterModule],
})
export class ActRoutingModule {}

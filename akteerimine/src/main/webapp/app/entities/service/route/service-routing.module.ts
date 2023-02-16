import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ServiceComponent } from '../list/service.component';
import { ASC } from 'app/config/navigation.constants';

const serviceRoute: Routes = [
  {
    path: '',
    component: ServiceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(serviceRoute)],
  exports: [RouterModule],
})
export class ServiceRoutingModule {}

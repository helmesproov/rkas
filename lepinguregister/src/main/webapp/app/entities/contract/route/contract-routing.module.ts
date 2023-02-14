import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContractComponent } from '../list/contract.component';
import { ASC } from 'app/config/navigation.constants';

const contractRoute: Routes = [
  {
    path: '',
    component: ContractComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(contractRoute)],
  exports: [RouterModule],
})
export class ContractRoutingModule {}

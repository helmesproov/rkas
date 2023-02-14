import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ContractRoutingModule } from './route/contract-routing.module';
import { ContractComponent } from './list/contract.component';
import { PendingContractComponent } from "./pending-list/pending-contract.component";

@NgModule({
  imports: [SharedModule, ContractRoutingModule],
  declarations: [ContractComponent, PendingContractComponent],
})
export class ContractModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'real-estate',
        data: { pageTitle: 'Objektid' },
        loadChildren: () => import('./real-estate/real-estate.module').then(m => m.RealEstateModule),
      },
      {
        path: 'contract',
        data: { pageTitle: 'global.title' },
        loadChildren: () => import('./contract/contract.module').then(m => m.ContractModule),
      },
      {
        path: 'service',
        data: { pageTitle: 'Teenused' },
        loadChildren: () => import('./service/service.module').then(m => m.ServiceModule),
      },
      {
        path: 'act',
        data: { pageTitle: 'Aktid' },
        loadChildren: () => import('./act/act.module').then(m => m.ActModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'act',
        data: { pageTitle: 'act.title' },
        loadChildren: () => import('./act/act.module').then(m => m.ActModule),
      },
    ]),
  ],
})
export class EntityRoutingModule {}

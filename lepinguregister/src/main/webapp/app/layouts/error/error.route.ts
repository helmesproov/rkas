import { Routes } from '@angular/router';

import { ErrorComponent } from './error.component';

export const errorRoute: Routes = [
  {
    path: 'error',
    component: ErrorComponent,
    data: {
      pageTitle: 'Vealeht!',
    },
  },
  {
    path: 'accessdenied',
    component: ErrorComponent,
    data: {
      pageTitle: 'Vealeht!',
      errorMessage: 'Teil puuduvad õigused selle lehe nägemiseks.',
    },
  },
  {
    path: '404',
    component: ErrorComponent,
    data: {
      pageTitle: 'Vealeht!',
      errorMessage: 'Lehte ei ole olemas.',
    },
  },
  {
    path: '**',
    redirectTo: '/404',
  },
];

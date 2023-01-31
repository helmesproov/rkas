import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ActComponent } from './list/act.component';
import { ActDetailComponent } from './detail/act-detail.component';
import { ActUpdateComponent } from './update/act-update.component';
import { ActDeleteDialogComponent } from './delete/act-delete-dialog.component';
import { ActRoutingModule } from './route/act-routing.module';

@NgModule({
  imports: [SharedModule, ActRoutingModule],
  declarations: [ActComponent, ActDetailComponent, ActUpdateComponent, ActDeleteDialogComponent],
})
export class ActModule {}

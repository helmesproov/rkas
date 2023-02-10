import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RealEstateComponent } from './list/real-estate.component';
import { RealEstateDetailComponent } from './detail/real-estate-detail.component';
import { RealEstateUpdateComponent } from './update/real-estate-update.component';
import { RealEstateDeleteDialogComponent } from './delete/real-estate-delete-dialog.component';
import { RealEstateRoutingModule } from './route/real-estate-routing.module';

@NgModule({
  imports: [SharedModule, RealEstateRoutingModule],
  declarations: [RealEstateComponent, RealEstateDetailComponent, RealEstateUpdateComponent, RealEstateDeleteDialogComponent],
})
export class RealEstateModule {}

import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ActComponent } from './list/act.component';
import { ActDetailComponent } from './detail/act-detail.component';
import { ActRoutingModule } from './route/act-routing.module';
import { RealEstateServicesModule } from "../real-estate-services/real-estate-services.module";

@NgModule({
    imports: [SharedModule, ActRoutingModule, RealEstateServicesModule],
  declarations: [ActComponent, ActDetailComponent],
})
export class ActModule {}

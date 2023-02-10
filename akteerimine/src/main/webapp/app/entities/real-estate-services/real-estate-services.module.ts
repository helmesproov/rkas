import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RealEstateServicesComponent } from "./list/real-estate-services.component";
import { RealEstateServiceModifyDialogComponent } from "./modify/real-estate-service-modify-dialog.component";
import { ServiceModule } from "../service/service.module";

@NgModule({
  imports: [SharedModule, ServiceModule],
  declarations: [RealEstateServicesComponent, RealEstateServiceModifyDialogComponent],
  exports: [
    RealEstateServicesComponent
  ]
})
export class RealEstateServicesModule {}

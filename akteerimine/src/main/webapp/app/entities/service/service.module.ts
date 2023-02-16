import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ServiceComponent } from './list/service.component';
import { ServiceRoutingModule } from './route/service-routing.module';

@NgModule({
    imports: [SharedModule, ServiceRoutingModule],
    declarations: [ServiceComponent],
    exports: [
        ServiceComponent
    ]
})
export class ServiceModule {}

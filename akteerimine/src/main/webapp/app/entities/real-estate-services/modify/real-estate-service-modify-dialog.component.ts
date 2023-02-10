import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { RealEstateServicesService } from "../service/real-estate-services.service";
import { IRealEstateService } from "../real-estate-services.model";

@Component({
    templateUrl: './real-estate-service-modify-dialog.component.html',
})
export class RealEstateServiceModifyDialogComponent {
    realEstate?: IRealEstateService;

    constructor(protected realEstateServicesService: RealEstateServicesService, protected activeModal: NgbActiveModal) {}

    cancel(): void {
        this.activeModal.dismiss();
    }

    confirmModify(id: number): void {
    }
}
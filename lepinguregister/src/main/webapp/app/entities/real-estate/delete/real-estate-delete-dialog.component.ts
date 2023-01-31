import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRealEstate } from '../real-estate.model';
import { RealEstateService } from '../service/real-estate.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './real-estate-delete-dialog.component.html',
})
export class RealEstateDeleteDialogComponent {
  realEstate?: IRealEstate;

  constructor(protected realEstateService: RealEstateService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.realEstateService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

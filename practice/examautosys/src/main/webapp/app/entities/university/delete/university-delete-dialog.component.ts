import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUniversity } from '../university.model';
import { UniversityService } from '../service/university.service';

@Component({
  templateUrl: './university-delete-dialog.component.html',
})
export class UniversityDeleteDialogComponent {
  university?: IUniversity;

  constructor(protected universityService: UniversityService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.universityService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

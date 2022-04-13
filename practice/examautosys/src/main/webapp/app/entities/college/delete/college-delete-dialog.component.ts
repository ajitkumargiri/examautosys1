import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICollege } from '../college.model';
import { CollegeService } from '../service/college.service';

@Component({
  templateUrl: './college-delete-dialog.component.html',
})
export class CollegeDeleteDialogComponent {
  college?: ICollege;

  constructor(protected collegeService: CollegeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.collegeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

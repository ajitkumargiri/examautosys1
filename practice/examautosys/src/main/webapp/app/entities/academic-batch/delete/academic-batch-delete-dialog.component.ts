import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAcademicBatch } from '../academic-batch.model';
import { AcademicBatchService } from '../service/academic-batch.service';

@Component({
  templateUrl: './academic-batch-delete-dialog.component.html',
})
export class AcademicBatchDeleteDialogComponent {
  academicBatch?: IAcademicBatch;

  constructor(protected academicBatchService: AcademicBatchService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.academicBatchService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

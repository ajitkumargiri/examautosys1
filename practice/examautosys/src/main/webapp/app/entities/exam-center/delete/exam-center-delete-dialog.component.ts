import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IExamCenter } from '../exam-center.model';
import { ExamCenterService } from '../service/exam-center.service';

@Component({
  templateUrl: './exam-center-delete-dialog.component.html',
})
export class ExamCenterDeleteDialogComponent {
  examCenter?: IExamCenter;

  constructor(protected examCenterService: ExamCenterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.examCenterService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

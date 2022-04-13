import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IExamApplicationForm } from '../exam-application-form.model';
import { ExamApplicationFormService } from '../service/exam-application-form.service';

@Component({
  templateUrl: './exam-application-form-delete-dialog.component.html',
})
export class ExamApplicationFormDeleteDialogComponent {
  examApplicationForm?: IExamApplicationForm;

  constructor(protected examApplicationFormService: ExamApplicationFormService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.examApplicationFormService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

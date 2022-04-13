import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubjectPaper } from '../subject-paper.model';
import { SubjectPaperService } from '../service/subject-paper.service';

@Component({
  templateUrl: './subject-paper-delete-dialog.component.html',
})
export class SubjectPaperDeleteDialogComponent {
  subjectPaper?: ISubjectPaper;

  constructor(protected subjectPaperService: SubjectPaperService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subjectPaperService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

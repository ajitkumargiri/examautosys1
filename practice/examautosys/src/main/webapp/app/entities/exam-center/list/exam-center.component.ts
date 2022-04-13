import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IExamCenter } from '../exam-center.model';
import { ExamCenterService } from '../service/exam-center.service';
import { ExamCenterDeleteDialogComponent } from '../delete/exam-center-delete-dialog.component';

@Component({
  selector: 'jhi-exam-center',
  templateUrl: './exam-center.component.html',
})
export class ExamCenterComponent implements OnInit {
  examCenters?: IExamCenter[];
  isLoading = false;

  constructor(protected examCenterService: ExamCenterService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.examCenterService.query().subscribe({
      next: (res: HttpResponse<IExamCenter[]>) => {
        this.isLoading = false;
        this.examCenters = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IExamCenter): number {
    return item.id!;
  }

  delete(examCenter: IExamCenter): void {
    const modalRef = this.modalService.open(ExamCenterDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.examCenter = examCenter;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUniversity } from '../university.model';
import { UniversityService } from '../service/university.service';
import { UniversityDeleteDialogComponent } from '../delete/university-delete-dialog.component';

@Component({
  selector: 'jhi-university',
  templateUrl: './university.component.html',
})
export class UniversityComponent implements OnInit {
  universities?: IUniversity[];
  isLoading = false;

  constructor(protected universityService: UniversityService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.universityService.query().subscribe({
      next: (res: HttpResponse<IUniversity[]>) => {
        this.isLoading = false;
        this.universities = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IUniversity): number {
    return item.id!;
  }

  delete(university: IUniversity): void {
    const modalRef = this.modalService.open(UniversityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.university = university;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}

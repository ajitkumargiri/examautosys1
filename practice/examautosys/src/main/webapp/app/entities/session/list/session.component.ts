import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISession } from '../session.model';
import { SessionService } from '../service/session.service';
import { SessionDeleteDialogComponent } from '../delete/session-delete-dialog.component';

@Component({
  selector: 'jhi-session',
  templateUrl: './session.component.html',
})
export class SessionComponent implements OnInit {
  sessions?: ISession[];
  isLoading = false;

  constructor(protected sessionService: SessionService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.sessionService.query().subscribe({
      next: (res: HttpResponse<ISession[]>) => {
        this.isLoading = false;
        this.sessions = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ISession): number {
    return item.id!;
  }

  delete(session: ISession): void {
    const modalRef = this.modalService.open(SessionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.session = session;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}

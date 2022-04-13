import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISession, Session } from '../session.model';
import { SessionService } from '../service/session.service';
import { IAcademicBatch } from 'app/entities/academic-batch/academic-batch.model';
import { AcademicBatchService } from 'app/entities/academic-batch/service/academic-batch.service';

@Component({
  selector: 'jhi-session-update',
  templateUrl: './session-update.component.html',
})
export class SessionUpdateComponent implements OnInit {
  isSaving = false;

  academicBatchesSharedCollection: IAcademicBatch[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    code: [],
    startDate: [],
    endDate: [],
    academicBatch: [],
  });

  constructor(
    protected sessionService: SessionService,
    protected academicBatchService: AcademicBatchService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ session }) => {
      this.updateForm(session);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const session = this.createFromForm();
    if (session.id !== undefined) {
      this.subscribeToSaveResponse(this.sessionService.update(session));
    } else {
      this.subscribeToSaveResponse(this.sessionService.create(session));
    }
  }

  trackAcademicBatchById(_index: number, item: IAcademicBatch): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISession>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(session: ISession): void {
    this.editForm.patchValue({
      id: session.id,
      name: session.name,
      code: session.code,
      startDate: session.startDate,
      endDate: session.endDate,
      academicBatch: session.academicBatch,
    });

    this.academicBatchesSharedCollection = this.academicBatchService.addAcademicBatchToCollectionIfMissing(
      this.academicBatchesSharedCollection,
      session.academicBatch
    );
  }

  protected loadRelationshipsOptions(): void {
    this.academicBatchService
      .query()
      .pipe(map((res: HttpResponse<IAcademicBatch[]>) => res.body ?? []))
      .pipe(
        map((academicBatches: IAcademicBatch[]) =>
          this.academicBatchService.addAcademicBatchToCollectionIfMissing(academicBatches, this.editForm.get('academicBatch')!.value)
        )
      )
      .subscribe((academicBatches: IAcademicBatch[]) => (this.academicBatchesSharedCollection = academicBatches));
  }

  protected createFromForm(): ISession {
    return {
      ...new Session(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      academicBatch: this.editForm.get(['academicBatch'])!.value,
    };
  }
}

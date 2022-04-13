import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISubjectPaper, SubjectPaper } from '../subject-paper.model';
import { SubjectPaperService } from '../service/subject-paper.service';
import { ISession } from 'app/entities/session/session.model';
import { SessionService } from 'app/entities/session/service/session.service';
import { SubjectPaperType } from 'app/entities/enumerations/subject-paper-type.model';

@Component({
  selector: 'jhi-subject-paper-update',
  templateUrl: './subject-paper-update.component.html',
})
export class SubjectPaperUpdateComponent implements OnInit {
  isSaving = false;
  subjectPaperTypeValues = Object.keys(SubjectPaperType);

  sessionsSharedCollection: ISession[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    code: [],
    type: [],
    fullMark: [],
    passMark: [],
    session: [],
  });

  constructor(
    protected subjectPaperService: SubjectPaperService,
    protected sessionService: SessionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subjectPaper }) => {
      this.updateForm(subjectPaper);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subjectPaper = this.createFromForm();
    if (subjectPaper.id !== undefined) {
      this.subscribeToSaveResponse(this.subjectPaperService.update(subjectPaper));
    } else {
      this.subscribeToSaveResponse(this.subjectPaperService.create(subjectPaper));
    }
  }

  trackSessionById(_index: number, item: ISession): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubjectPaper>>): void {
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

  protected updateForm(subjectPaper: ISubjectPaper): void {
    this.editForm.patchValue({
      id: subjectPaper.id,
      name: subjectPaper.name,
      code: subjectPaper.code,
      type: subjectPaper.type,
      fullMark: subjectPaper.fullMark,
      passMark: subjectPaper.passMark,
      session: subjectPaper.session,
    });

    this.sessionsSharedCollection = this.sessionService.addSessionToCollectionIfMissing(
      this.sessionsSharedCollection,
      subjectPaper.session
    );
  }

  protected loadRelationshipsOptions(): void {
    this.sessionService
      .query()
      .pipe(map((res: HttpResponse<ISession[]>) => res.body ?? []))
      .pipe(
        map((sessions: ISession[]) => this.sessionService.addSessionToCollectionIfMissing(sessions, this.editForm.get('session')!.value))
      )
      .subscribe((sessions: ISession[]) => (this.sessionsSharedCollection = sessions));
  }

  protected createFromForm(): ISubjectPaper {
    return {
      ...new SubjectPaper(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      type: this.editForm.get(['type'])!.value,
      fullMark: this.editForm.get(['fullMark'])!.value,
      passMark: this.editForm.get(['passMark'])!.value,
      session: this.editForm.get(['session'])!.value,
    };
  }
}

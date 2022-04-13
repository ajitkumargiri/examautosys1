import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IExam, Exam } from '../exam.model';
import { ExamService } from '../service/exam.service';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { ISession } from 'app/entities/session/session.model';
import { SessionService } from 'app/entities/session/service/session.service';
import { ExamType } from 'app/entities/enumerations/exam-type.model';

@Component({
  selector: 'jhi-exam-update',
  templateUrl: './exam-update.component.html',
})
export class ExamUpdateComponent implements OnInit {
  isSaving = false;
  examTypeValues = Object.keys(ExamType);

  correspondingAddressesCollection: IAddress[] = [];
  permanentAddressesCollection: IAddress[] = [];
  sessionsSharedCollection: ISession[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    type: [null, [Validators.required]],
    code: [],
    date: [],
    startTime: [],
    endTime: [],
    correspondingAddress: [],
    permanentAddress: [],
    session: [],
  });

  constructor(
    protected examService: ExamService,
    protected addressService: AddressService,
    protected sessionService: SessionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ exam }) => {
      if (exam.id === undefined) {
        const today = dayjs().startOf('day');
        exam.date = today;
      }

      this.updateForm(exam);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const exam = this.createFromForm();
    if (exam.id !== undefined) {
      this.subscribeToSaveResponse(this.examService.update(exam));
    } else {
      this.subscribeToSaveResponse(this.examService.create(exam));
    }
  }

  trackAddressById(_index: number, item: IAddress): number {
    return item.id!;
  }

  trackSessionById(_index: number, item: ISession): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExam>>): void {
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

  protected updateForm(exam: IExam): void {
    this.editForm.patchValue({
      id: exam.id,
      name: exam.name,
      type: exam.type,
      code: exam.code,
      date: exam.date ? exam.date.format(DATE_TIME_FORMAT) : null,
      startTime: exam.startTime,
      endTime: exam.endTime,
      correspondingAddress: exam.correspondingAddress,
      permanentAddress: exam.permanentAddress,
      session: exam.session,
    });

    this.correspondingAddressesCollection = this.addressService.addAddressToCollectionIfMissing(
      this.correspondingAddressesCollection,
      exam.correspondingAddress
    );
    this.permanentAddressesCollection = this.addressService.addAddressToCollectionIfMissing(
      this.permanentAddressesCollection,
      exam.permanentAddress
    );
    this.sessionsSharedCollection = this.sessionService.addSessionToCollectionIfMissing(this.sessionsSharedCollection, exam.session);
  }

  protected loadRelationshipsOptions(): void {
    this.addressService
      .query({ filter: 'exam-is-null' })
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddress[]) =>
          this.addressService.addAddressToCollectionIfMissing(addresses, this.editForm.get('correspondingAddress')!.value)
        )
      )
      .subscribe((addresses: IAddress[]) => (this.correspondingAddressesCollection = addresses));

    this.addressService
      .query({ filter: 'exam-is-null' })
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddress[]) =>
          this.addressService.addAddressToCollectionIfMissing(addresses, this.editForm.get('permanentAddress')!.value)
        )
      )
      .subscribe((addresses: IAddress[]) => (this.permanentAddressesCollection = addresses));

    this.sessionService
      .query()
      .pipe(map((res: HttpResponse<ISession[]>) => res.body ?? []))
      .pipe(
        map((sessions: ISession[]) => this.sessionService.addSessionToCollectionIfMissing(sessions, this.editForm.get('session')!.value))
      )
      .subscribe((sessions: ISession[]) => (this.sessionsSharedCollection = sessions));
  }

  protected createFromForm(): IExam {
    return {
      ...new Exam(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      type: this.editForm.get(['type'])!.value,
      code: this.editForm.get(['code'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      startTime: this.editForm.get(['startTime'])!.value,
      endTime: this.editForm.get(['endTime'])!.value,
      correspondingAddress: this.editForm.get(['correspondingAddress'])!.value,
      permanentAddress: this.editForm.get(['permanentAddress'])!.value,
      session: this.editForm.get(['session'])!.value,
    };
  }
}

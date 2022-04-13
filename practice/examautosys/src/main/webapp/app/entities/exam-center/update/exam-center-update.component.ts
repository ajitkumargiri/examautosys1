import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IExamCenter, ExamCenter } from '../exam-center.model';
import { ExamCenterService } from '../service/exam-center.service';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IExam } from 'app/entities/exam/exam.model';
import { ExamService } from 'app/entities/exam/service/exam.service';

@Component({
  selector: 'jhi-exam-center-update',
  templateUrl: './exam-center-update.component.html',
})
export class ExamCenterUpdateComponent implements OnInit {
  isSaving = false;

  addressesCollection: IAddress[] = [];
  examsSharedCollection: IExam[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    code: [],
    address: [],
    exam: [],
  });

  constructor(
    protected examCenterService: ExamCenterService,
    protected addressService: AddressService,
    protected examService: ExamService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examCenter }) => {
      this.updateForm(examCenter);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const examCenter = this.createFromForm();
    if (examCenter.id !== undefined) {
      this.subscribeToSaveResponse(this.examCenterService.update(examCenter));
    } else {
      this.subscribeToSaveResponse(this.examCenterService.create(examCenter));
    }
  }

  trackAddressById(_index: number, item: IAddress): number {
    return item.id!;
  }

  trackExamById(_index: number, item: IExam): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamCenter>>): void {
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

  protected updateForm(examCenter: IExamCenter): void {
    this.editForm.patchValue({
      id: examCenter.id,
      name: examCenter.name,
      code: examCenter.code,
      address: examCenter.address,
      exam: examCenter.exam,
    });

    this.addressesCollection = this.addressService.addAddressToCollectionIfMissing(this.addressesCollection, examCenter.address);
    this.examsSharedCollection = this.examService.addExamToCollectionIfMissing(this.examsSharedCollection, examCenter.exam);
  }

  protected loadRelationshipsOptions(): void {
    this.addressService
      .query({ filter: 'examcenter-is-null' })
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddress[]) => this.addressService.addAddressToCollectionIfMissing(addresses, this.editForm.get('address')!.value))
      )
      .subscribe((addresses: IAddress[]) => (this.addressesCollection = addresses));

    this.examService
      .query()
      .pipe(map((res: HttpResponse<IExam[]>) => res.body ?? []))
      .pipe(map((exams: IExam[]) => this.examService.addExamToCollectionIfMissing(exams, this.editForm.get('exam')!.value)))
      .subscribe((exams: IExam[]) => (this.examsSharedCollection = exams));
  }

  protected createFromForm(): IExamCenter {
    return {
      ...new ExamCenter(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      address: this.editForm.get(['address'])!.value,
      exam: this.editForm.get(['exam'])!.value,
    };
  }
}

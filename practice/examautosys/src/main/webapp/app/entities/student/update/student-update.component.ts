import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IStudent, Student } from '../student.model';
import { StudentService } from '../service/student.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IAcademicBatch } from 'app/entities/academic-batch/academic-batch.model';
import { AcademicBatchService } from 'app/entities/academic-batch/service/academic-batch.service';
import { Gender } from 'app/entities/enumerations/gender.model';
import { MaritialStatus } from 'app/entities/enumerations/maritial-status.model';
import { BloodGroup } from 'app/entities/enumerations/blood-group.model';

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html',
})
export class StudentUpdateComponent implements OnInit {
  isSaving = false;
  genderValues = Object.keys(Gender);
  maritialStatusValues = Object.keys(MaritialStatus);
  bloodGroupValues = Object.keys(BloodGroup);

  academicBatchesSharedCollection: IAcademicBatch[] = [];

  editForm = this.fb.group({
    id: [],
    regNumber: [null, []],
    firstName: [null, [Validators.required]],
    middleName: [],
    lastName: [null, [Validators.required]],
    dob: [],
    fatherName: [],
    motherName: [],
    email: [null, [Validators.required]],
    mobileNumber: [null, [Validators.required]],
    nationality: [null, [Validators.required]],
    gender: [],
    religion: [],
    catagory: [],
    maritialStatus: [],
    adharNumber: [null, [Validators.required]],
    bloodGroup: [],
    fatherMobileNumber: [],
    fatherEmailId: [],
    image: [null, []],
    imageContentType: [],
    signature: [null, []],
    signatureContentType: [],
    adhar: [null, []],
    adharContentType: [],
    academicBatch: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected studentService: StudentService,
    protected academicBatchService: AcademicBatchService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ student }) => {
      if (student.id === undefined) {
        const today = dayjs().startOf('day');
        student.dob = today;
      }

      this.updateForm(student);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('examautosysApp.error', { message: err.message })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  trackAcademicBatchById(_index: number, item: IAcademicBatch): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>): void {
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

  protected updateForm(student: IStudent): void {
    this.editForm.patchValue({
      id: student.id,
      regNumber: student.regNumber,
      firstName: student.firstName,
      middleName: student.middleName,
      lastName: student.lastName,
      dob: student.dob ? student.dob.format(DATE_TIME_FORMAT) : null,
      fatherName: student.fatherName,
      motherName: student.motherName,
      email: student.email,
      mobileNumber: student.mobileNumber,
      nationality: student.nationality,
      gender: student.gender,
      religion: student.religion,
      catagory: student.catagory,
      maritialStatus: student.maritialStatus,
      adharNumber: student.adharNumber,
      bloodGroup: student.bloodGroup,
      fatherMobileNumber: student.fatherMobileNumber,
      fatherEmailId: student.fatherEmailId,
      image: student.image,
      imageContentType: student.imageContentType,
      signature: student.signature,
      signatureContentType: student.signatureContentType,
      adhar: student.adhar,
      adharContentType: student.adharContentType,
      academicBatch: student.academicBatch,
    });

    this.academicBatchesSharedCollection = this.academicBatchService.addAcademicBatchToCollectionIfMissing(
      this.academicBatchesSharedCollection,
      student.academicBatch
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

  protected createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id'])!.value,
      regNumber: this.editForm.get(['regNumber'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      dob: this.editForm.get(['dob'])!.value ? dayjs(this.editForm.get(['dob'])!.value, DATE_TIME_FORMAT) : undefined,
      fatherName: this.editForm.get(['fatherName'])!.value,
      motherName: this.editForm.get(['motherName'])!.value,
      email: this.editForm.get(['email'])!.value,
      mobileNumber: this.editForm.get(['mobileNumber'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      religion: this.editForm.get(['religion'])!.value,
      catagory: this.editForm.get(['catagory'])!.value,
      maritialStatus: this.editForm.get(['maritialStatus'])!.value,
      adharNumber: this.editForm.get(['adharNumber'])!.value,
      bloodGroup: this.editForm.get(['bloodGroup'])!.value,
      fatherMobileNumber: this.editForm.get(['fatherMobileNumber'])!.value,
      fatherEmailId: this.editForm.get(['fatherEmailId'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      signatureContentType: this.editForm.get(['signatureContentType'])!.value,
      signature: this.editForm.get(['signature'])!.value,
      adharContentType: this.editForm.get(['adharContentType'])!.value,
      adhar: this.editForm.get(['adhar'])!.value,
      academicBatch: this.editForm.get(['academicBatch'])!.value,
    };
  }
}

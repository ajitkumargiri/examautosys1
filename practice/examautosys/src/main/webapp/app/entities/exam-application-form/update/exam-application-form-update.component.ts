import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IExamApplicationForm, ExamApplicationForm } from '../exam-application-form.model';
import { ExamApplicationFormService } from '../service/exam-application-form.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IStudent } from 'app/entities/student/student.model';
import { StudentService } from 'app/entities/student/service/student.service';
import { IExam } from 'app/entities/exam/exam.model';
import { ExamService } from 'app/entities/exam/service/exam.service';
import { IExamCenter } from 'app/entities/exam-center/exam-center.model';
import { ExamCenterService } from 'app/entities/exam-center/service/exam-center.service';
import { Gender } from 'app/entities/enumerations/gender.model';
import { BloodGroup } from 'app/entities/enumerations/blood-group.model';
import { IdentityType } from 'app/entities/enumerations/identity-type.model';

@Component({
  selector: 'jhi-exam-application-form-update',
  templateUrl: './exam-application-form-update.component.html',
})
export class ExamApplicationFormUpdateComponent implements OnInit {
  isSaving = false;
  genderValues = Object.keys(Gender);
  bloodGroupValues = Object.keys(BloodGroup);
  identityTypeValues = Object.keys(IdentityType);

  correspondingAddressesCollection: IAddress[] = [];
  studentsSharedCollection: IStudent[] = [];
  examsSharedCollection: IExam[] = [];
  examCentersSharedCollection: IExamCenter[] = [];

  editForm = this.fb.group({
    id: [],
    regNumber: [null, []],
    firstName: [null, [Validators.required]],
    middleName: [],
    lastName: [null, [Validators.required]],
    dob: [],
    fatherName: [],
    email: [null, [Validators.required]],
    mobileNumber: [null, [Validators.required]],
    nationality: [null, [Validators.required]],
    gender: [],
    religion: [],
    adharNumber: [null, [Validators.required]],
    bloodGroup: [],
    isApproved: [],
    catagory: [],
    identityType: [null, [Validators.required]],
    identityNumber: [null, [Validators.required]],
    image: [],
    imageContentType: [],
    signature: [],
    signatureContentType: [],
    adhar: [],
    adharContentType: [],
    imagePath: [],
    signPath: [],
    adharPath: [],
    correspondingAddress: [],
    student: [],
    exam: [],
    examCenter: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected examApplicationFormService: ExamApplicationFormService,
    protected addressService: AddressService,
    protected studentService: StudentService,
    protected examService: ExamService,
    protected examCenterService: ExamCenterService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examApplicationForm }) => {
      if (examApplicationForm.id === undefined) {
        const today = dayjs().startOf('day');
        examApplicationForm.dob = today;
      }

      this.updateForm(examApplicationForm);

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
    const examApplicationForm = this.createFromForm();
    if (examApplicationForm.id !== undefined) {
      this.subscribeToSaveResponse(this.examApplicationFormService.update(examApplicationForm));
    } else {
      this.subscribeToSaveResponse(this.examApplicationFormService.create(examApplicationForm));
    }
  }

  trackAddressById(_index: number, item: IAddress): number {
    return item.id!;
  }

  trackStudentById(_index: number, item: IStudent): number {
    return item.id!;
  }

  trackExamById(_index: number, item: IExam): number {
    return item.id!;
  }

  trackExamCenterById(_index: number, item: IExamCenter): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamApplicationForm>>): void {
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

  protected updateForm(examApplicationForm: IExamApplicationForm): void {
    this.editForm.patchValue({
      id: examApplicationForm.id,
      regNumber: examApplicationForm.regNumber,
      firstName: examApplicationForm.firstName,
      middleName: examApplicationForm.middleName,
      lastName: examApplicationForm.lastName,
      dob: examApplicationForm.dob ? examApplicationForm.dob.format(DATE_TIME_FORMAT) : null,
      fatherName: examApplicationForm.fatherName,
      email: examApplicationForm.email,
      mobileNumber: examApplicationForm.mobileNumber,
      nationality: examApplicationForm.nationality,
      gender: examApplicationForm.gender,
      religion: examApplicationForm.religion,
      adharNumber: examApplicationForm.adharNumber,
      bloodGroup: examApplicationForm.bloodGroup,
      isApproved: examApplicationForm.isApproved,
      catagory: examApplicationForm.catagory,
      identityType: examApplicationForm.identityType,
      identityNumber: examApplicationForm.identityNumber,
      image: examApplicationForm.image,
      imageContentType: examApplicationForm.imageContentType,
      signature: examApplicationForm.signature,
      signatureContentType: examApplicationForm.signatureContentType,
      adhar: examApplicationForm.adhar,
      adharContentType: examApplicationForm.adharContentType,
      imagePath: examApplicationForm.imagePath,
      signPath: examApplicationForm.signPath,
      adharPath: examApplicationForm.adharPath,
      correspondingAddress: examApplicationForm.correspondingAddress,
      student: examApplicationForm.student,
      exam: examApplicationForm.exam,
      examCenter: examApplicationForm.examCenter,
    });

    this.correspondingAddressesCollection = this.addressService.addAddressToCollectionIfMissing(
      this.correspondingAddressesCollection,
      examApplicationForm.correspondingAddress
    );
    this.studentsSharedCollection = this.studentService.addStudentToCollectionIfMissing(
      this.studentsSharedCollection,
      examApplicationForm.student
    );
    this.examsSharedCollection = this.examService.addExamToCollectionIfMissing(this.examsSharedCollection, examApplicationForm.exam);
    this.examCentersSharedCollection = this.examCenterService.addExamCenterToCollectionIfMissing(
      this.examCentersSharedCollection,
      examApplicationForm.examCenter
    );
  }

  protected loadRelationshipsOptions(): void {
    this.addressService
      .query({ filter: 'examapplicationform-is-null' })
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddress[]) =>
          this.addressService.addAddressToCollectionIfMissing(addresses, this.editForm.get('correspondingAddress')!.value)
        )
      )
      .subscribe((addresses: IAddress[]) => (this.correspondingAddressesCollection = addresses));

    this.studentService
      .query()
      .pipe(map((res: HttpResponse<IStudent[]>) => res.body ?? []))
      .pipe(
        map((students: IStudent[]) => this.studentService.addStudentToCollectionIfMissing(students, this.editForm.get('student')!.value))
      )
      .subscribe((students: IStudent[]) => (this.studentsSharedCollection = students));

    this.examService
      .query()
      .pipe(map((res: HttpResponse<IExam[]>) => res.body ?? []))
      .pipe(map((exams: IExam[]) => this.examService.addExamToCollectionIfMissing(exams, this.editForm.get('exam')!.value)))
      .subscribe((exams: IExam[]) => (this.examsSharedCollection = exams));

    this.examCenterService
      .query()
      .pipe(map((res: HttpResponse<IExamCenter[]>) => res.body ?? []))
      .pipe(
        map((examCenters: IExamCenter[]) =>
          this.examCenterService.addExamCenterToCollectionIfMissing(examCenters, this.editForm.get('examCenter')!.value)
        )
      )
      .subscribe((examCenters: IExamCenter[]) => (this.examCentersSharedCollection = examCenters));
  }

  protected createFromForm(): IExamApplicationForm {
    return {
      ...new ExamApplicationForm(),
      id: this.editForm.get(['id'])!.value,
      regNumber: this.editForm.get(['regNumber'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      dob: this.editForm.get(['dob'])!.value ? dayjs(this.editForm.get(['dob'])!.value, DATE_TIME_FORMAT) : undefined,
      fatherName: this.editForm.get(['fatherName'])!.value,
      email: this.editForm.get(['email'])!.value,
      mobileNumber: this.editForm.get(['mobileNumber'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      religion: this.editForm.get(['religion'])!.value,
      adharNumber: this.editForm.get(['adharNumber'])!.value,
      bloodGroup: this.editForm.get(['bloodGroup'])!.value,
      isApproved: this.editForm.get(['isApproved'])!.value,
      catagory: this.editForm.get(['catagory'])!.value,
      identityType: this.editForm.get(['identityType'])!.value,
      identityNumber: this.editForm.get(['identityNumber'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      signatureContentType: this.editForm.get(['signatureContentType'])!.value,
      signature: this.editForm.get(['signature'])!.value,
      adharContentType: this.editForm.get(['adharContentType'])!.value,
      adhar: this.editForm.get(['adhar'])!.value,
      imagePath: this.editForm.get(['imagePath'])!.value,
      signPath: this.editForm.get(['signPath'])!.value,
      adharPath: this.editForm.get(['adharPath'])!.value,
      correspondingAddress: this.editForm.get(['correspondingAddress'])!.value,
      student: this.editForm.get(['student'])!.value,
      exam: this.editForm.get(['exam'])!.value,
      examCenter: this.editForm.get(['examCenter'])!.value,
    };
  }
}

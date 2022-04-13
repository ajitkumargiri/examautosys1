import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICollege, College } from '../college.model';
import { CollegeService } from '../service/college.service';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IUniversity } from 'app/entities/university/university.model';
import { UniversityService } from 'app/entities/university/service/university.service';

@Component({
  selector: 'jhi-college-update',
  templateUrl: './college-update.component.html',
})
export class CollegeUpdateComponent implements OnInit {
  isSaving = false;

  addressesCollection: IAddress[] = [];
  universitiesSharedCollection: IUniversity[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    code: [],
    address: [],
    university: [],
  });

  constructor(
    protected collegeService: CollegeService,
    protected addressService: AddressService,
    protected universityService: UniversityService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ college }) => {
      this.updateForm(college);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const college = this.createFromForm();
    if (college.id !== undefined) {
      this.subscribeToSaveResponse(this.collegeService.update(college));
    } else {
      this.subscribeToSaveResponse(this.collegeService.create(college));
    }
  }

  trackAddressById(_index: number, item: IAddress): number {
    return item.id!;
  }

  trackUniversityById(_index: number, item: IUniversity): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollege>>): void {
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

  protected updateForm(college: ICollege): void {
    this.editForm.patchValue({
      id: college.id,
      name: college.name,
      code: college.code,
      address: college.address,
      university: college.university,
    });

    this.addressesCollection = this.addressService.addAddressToCollectionIfMissing(this.addressesCollection, college.address);
    this.universitiesSharedCollection = this.universityService.addUniversityToCollectionIfMissing(
      this.universitiesSharedCollection,
      college.university
    );
  }

  protected loadRelationshipsOptions(): void {
    this.addressService
      .query({ filter: 'college-is-null' })
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddress[]) => this.addressService.addAddressToCollectionIfMissing(addresses, this.editForm.get('address')!.value))
      )
      .subscribe((addresses: IAddress[]) => (this.addressesCollection = addresses));

    this.universityService
      .query()
      .pipe(map((res: HttpResponse<IUniversity[]>) => res.body ?? []))
      .pipe(
        map((universities: IUniversity[]) =>
          this.universityService.addUniversityToCollectionIfMissing(universities, this.editForm.get('university')!.value)
        )
      )
      .subscribe((universities: IUniversity[]) => (this.universitiesSharedCollection = universities));
  }

  protected createFromForm(): ICollege {
    return {
      ...new College(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      address: this.editForm.get(['address'])!.value,
      university: this.editForm.get(['university'])!.value,
    };
  }
}

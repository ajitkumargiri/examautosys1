import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IUniversity, University } from '../university.model';
import { UniversityService } from '../service/university.service';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';

@Component({
  selector: 'jhi-university-update',
  templateUrl: './university-update.component.html',
})
export class UniversityUpdateComponent implements OnInit {
  isSaving = false;

  addressesCollection: IAddress[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    code: [],
    address: [],
  });

  constructor(
    protected universityService: UniversityService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ university }) => {
      this.updateForm(university);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const university = this.createFromForm();
    if (university.id !== undefined) {
      this.subscribeToSaveResponse(this.universityService.update(university));
    } else {
      this.subscribeToSaveResponse(this.universityService.create(university));
    }
  }

  trackAddressById(_index: number, item: IAddress): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUniversity>>): void {
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

  protected updateForm(university: IUniversity): void {
    this.editForm.patchValue({
      id: university.id,
      name: university.name,
      code: university.code,
      address: university.address,
    });

    this.addressesCollection = this.addressService.addAddressToCollectionIfMissing(this.addressesCollection, university.address);
  }

  protected loadRelationshipsOptions(): void {
    this.addressService
      .query({ filter: 'university-is-null' })
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddress[]) => this.addressService.addAddressToCollectionIfMissing(addresses, this.editForm.get('address')!.value))
      )
      .subscribe((addresses: IAddress[]) => (this.addressesCollection = addresses));
  }

  protected createFromForm(): IUniversity {
    return {
      ...new University(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      address: this.editForm.get(['address'])!.value,
    };
  }
}

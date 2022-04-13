import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAcademicBatch, AcademicBatch } from '../academic-batch.model';
import { AcademicBatchService } from '../service/academic-batch.service';
import { IBranch } from 'app/entities/branch/branch.model';
import { BranchService } from 'app/entities/branch/service/branch.service';

@Component({
  selector: 'jhi-academic-batch-update',
  templateUrl: './academic-batch-update.component.html',
})
export class AcademicBatchUpdateComponent implements OnInit {
  isSaving = false;

  branchesSharedCollection: IBranch[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    code: [],
    academicStartYear: [null, [Validators.required]],
    academicEndYear: [null, [Validators.required]],
    branch: [],
  });

  constructor(
    protected academicBatchService: AcademicBatchService,
    protected branchService: BranchService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ academicBatch }) => {
      this.updateForm(academicBatch);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const academicBatch = this.createFromForm();
    if (academicBatch.id !== undefined) {
      this.subscribeToSaveResponse(this.academicBatchService.update(academicBatch));
    } else {
      this.subscribeToSaveResponse(this.academicBatchService.create(academicBatch));
    }
  }

  trackBranchById(_index: number, item: IBranch): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAcademicBatch>>): void {
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

  protected updateForm(academicBatch: IAcademicBatch): void {
    this.editForm.patchValue({
      id: academicBatch.id,
      name: academicBatch.name,
      code: academicBatch.code,
      academicStartYear: academicBatch.academicStartYear,
      academicEndYear: academicBatch.academicEndYear,
      branch: academicBatch.branch,
    });

    this.branchesSharedCollection = this.branchService.addBranchToCollectionIfMissing(this.branchesSharedCollection, academicBatch.branch);
  }

  protected loadRelationshipsOptions(): void {
    this.branchService
      .query()
      .pipe(map((res: HttpResponse<IBranch[]>) => res.body ?? []))
      .pipe(map((branches: IBranch[]) => this.branchService.addBranchToCollectionIfMissing(branches, this.editForm.get('branch')!.value)))
      .subscribe((branches: IBranch[]) => (this.branchesSharedCollection = branches));
  }

  protected createFromForm(): IAcademicBatch {
    return {
      ...new AcademicBatch(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      academicStartYear: this.editForm.get(['academicStartYear'])!.value,
      academicEndYear: this.editForm.get(['academicEndYear'])!.value,
      branch: this.editForm.get(['branch'])!.value,
    };
  }
}

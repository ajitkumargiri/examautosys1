import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICourse, Course } from '../course.model';
import { CourseService } from '../service/course.service';
import { ICollege } from 'app/entities/college/college.model';
import { CollegeService } from 'app/entities/college/service/college.service';

@Component({
  selector: 'jhi-course-update',
  templateUrl: './course-update.component.html',
})
export class CourseUpdateComponent implements OnInit {
  isSaving = false;

  collegesSharedCollection: ICollege[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    code: [],
    detals: [],
    college: [],
  });

  constructor(
    protected courseService: CourseService,
    protected collegeService: CollegeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ course }) => {
      this.updateForm(course);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const course = this.createFromForm();
    if (course.id !== undefined) {
      this.subscribeToSaveResponse(this.courseService.update(course));
    } else {
      this.subscribeToSaveResponse(this.courseService.create(course));
    }
  }

  trackCollegeById(_index: number, item: ICollege): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourse>>): void {
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

  protected updateForm(course: ICourse): void {
    this.editForm.patchValue({
      id: course.id,
      name: course.name,
      code: course.code,
      detals: course.detals,
      college: course.college,
    });

    this.collegesSharedCollection = this.collegeService.addCollegeToCollectionIfMissing(this.collegesSharedCollection, course.college);
  }

  protected loadRelationshipsOptions(): void {
    this.collegeService
      .query()
      .pipe(map((res: HttpResponse<ICollege[]>) => res.body ?? []))
      .pipe(
        map((colleges: ICollege[]) => this.collegeService.addCollegeToCollectionIfMissing(colleges, this.editForm.get('college')!.value))
      )
      .subscribe((colleges: ICollege[]) => (this.collegesSharedCollection = colleges));
  }

  protected createFromForm(): ICourse {
    return {
      ...new Course(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      detals: this.editForm.get(['detals'])!.value,
      college: this.editForm.get(['college'])!.value,
    };
  }
}

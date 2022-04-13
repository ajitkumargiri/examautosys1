import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAcademicBatch } from '../academic-batch.model';

@Component({
  selector: 'jhi-academic-batch-detail',
  templateUrl: './academic-batch-detail.component.html',
})
export class AcademicBatchDetailComponent implements OnInit {
  academicBatch: IAcademicBatch | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ academicBatch }) => {
      this.academicBatch = academicBatch;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

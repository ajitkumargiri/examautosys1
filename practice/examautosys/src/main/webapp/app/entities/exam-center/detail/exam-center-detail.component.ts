import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExamCenter } from '../exam-center.model';

@Component({
  selector: 'jhi-exam-center-detail',
  templateUrl: './exam-center-detail.component.html',
})
export class ExamCenterDetailComponent implements OnInit {
  examCenter: IExamCenter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examCenter }) => {
      this.examCenter = examCenter;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

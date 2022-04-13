import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubjectPaper } from '../subject-paper.model';

@Component({
  selector: 'jhi-subject-paper-detail',
  templateUrl: './subject-paper-detail.component.html',
})
export class SubjectPaperDetailComponent implements OnInit {
  subjectPaper: ISubjectPaper | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subjectPaper }) => {
      this.subjectPaper = subjectPaper;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IExamApplicationForm, ExamApplicationForm } from '../exam-application-form.model';
import { ExamApplicationFormService } from '../service/exam-application-form.service';

@Injectable({ providedIn: 'root' })
export class ExamApplicationFormRoutingResolveService implements Resolve<IExamApplicationForm> {
  constructor(protected service: ExamApplicationFormService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExamApplicationForm> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((examApplicationForm: HttpResponse<ExamApplicationForm>) => {
          if (examApplicationForm.body) {
            return of(examApplicationForm.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ExamApplicationForm());
  }
}

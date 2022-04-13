import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IExamCenter, ExamCenter } from '../exam-center.model';
import { ExamCenterService } from '../service/exam-center.service';

@Injectable({ providedIn: 'root' })
export class ExamCenterRoutingResolveService implements Resolve<IExamCenter> {
  constructor(protected service: ExamCenterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExamCenter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((examCenter: HttpResponse<ExamCenter>) => {
          if (examCenter.body) {
            return of(examCenter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ExamCenter());
  }
}

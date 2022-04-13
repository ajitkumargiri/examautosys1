import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubjectPaper, SubjectPaper } from '../subject-paper.model';
import { SubjectPaperService } from '../service/subject-paper.service';

@Injectable({ providedIn: 'root' })
export class SubjectPaperRoutingResolveService implements Resolve<ISubjectPaper> {
  constructor(protected service: SubjectPaperService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubjectPaper> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((subjectPaper: HttpResponse<SubjectPaper>) => {
          if (subjectPaper.body) {
            return of(subjectPaper.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubjectPaper());
  }
}

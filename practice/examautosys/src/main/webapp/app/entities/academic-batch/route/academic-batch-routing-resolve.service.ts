import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAcademicBatch, AcademicBatch } from '../academic-batch.model';
import { AcademicBatchService } from '../service/academic-batch.service';

@Injectable({ providedIn: 'root' })
export class AcademicBatchRoutingResolveService implements Resolve<IAcademicBatch> {
  constructor(protected service: AcademicBatchService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAcademicBatch> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((academicBatch: HttpResponse<AcademicBatch>) => {
          if (academicBatch.body) {
            return of(academicBatch.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AcademicBatch());
  }
}

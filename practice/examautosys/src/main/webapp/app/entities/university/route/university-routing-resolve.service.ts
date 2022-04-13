import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUniversity, University } from '../university.model';
import { UniversityService } from '../service/university.service';

@Injectable({ providedIn: 'root' })
export class UniversityRoutingResolveService implements Resolve<IUniversity> {
  constructor(protected service: UniversityService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUniversity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((university: HttpResponse<University>) => {
          if (university.body) {
            return of(university.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new University());
  }
}

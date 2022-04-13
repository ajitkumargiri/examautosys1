import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICollege, getCollegeIdentifier } from '../college.model';

export type EntityResponseType = HttpResponse<ICollege>;
export type EntityArrayResponseType = HttpResponse<ICollege[]>;

@Injectable({ providedIn: 'root' })
export class CollegeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/colleges');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(college: ICollege): Observable<EntityResponseType> {
    return this.http.post<ICollege>(this.resourceUrl, college, { observe: 'response' });
  }

  update(college: ICollege): Observable<EntityResponseType> {
    return this.http.put<ICollege>(`${this.resourceUrl}/${getCollegeIdentifier(college) as number}`, college, { observe: 'response' });
  }

  partialUpdate(college: ICollege): Observable<EntityResponseType> {
    return this.http.patch<ICollege>(`${this.resourceUrl}/${getCollegeIdentifier(college) as number}`, college, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICollege>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICollege[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCollegeToCollectionIfMissing(collegeCollection: ICollege[], ...collegesToCheck: (ICollege | null | undefined)[]): ICollege[] {
    const colleges: ICollege[] = collegesToCheck.filter(isPresent);
    if (colleges.length > 0) {
      const collegeCollectionIdentifiers = collegeCollection.map(collegeItem => getCollegeIdentifier(collegeItem)!);
      const collegesToAdd = colleges.filter(collegeItem => {
        const collegeIdentifier = getCollegeIdentifier(collegeItem);
        if (collegeIdentifier == null || collegeCollectionIdentifiers.includes(collegeIdentifier)) {
          return false;
        }
        collegeCollectionIdentifiers.push(collegeIdentifier);
        return true;
      });
      return [...collegesToAdd, ...collegeCollection];
    }
    return collegeCollection;
  }
}

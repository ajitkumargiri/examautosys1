import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IExamCenter, getExamCenterIdentifier } from '../exam-center.model';

export type EntityResponseType = HttpResponse<IExamCenter>;
export type EntityArrayResponseType = HttpResponse<IExamCenter[]>;

@Injectable({ providedIn: 'root' })
export class ExamCenterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/exam-centers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(examCenter: IExamCenter): Observable<EntityResponseType> {
    return this.http.post<IExamCenter>(this.resourceUrl, examCenter, { observe: 'response' });
  }

  update(examCenter: IExamCenter): Observable<EntityResponseType> {
    return this.http.put<IExamCenter>(`${this.resourceUrl}/${getExamCenterIdentifier(examCenter) as number}`, examCenter, {
      observe: 'response',
    });
  }

  partialUpdate(examCenter: IExamCenter): Observable<EntityResponseType> {
    return this.http.patch<IExamCenter>(`${this.resourceUrl}/${getExamCenterIdentifier(examCenter) as number}`, examCenter, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExamCenter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExamCenter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addExamCenterToCollectionIfMissing(
    examCenterCollection: IExamCenter[],
    ...examCentersToCheck: (IExamCenter | null | undefined)[]
  ): IExamCenter[] {
    const examCenters: IExamCenter[] = examCentersToCheck.filter(isPresent);
    if (examCenters.length > 0) {
      const examCenterCollectionIdentifiers = examCenterCollection.map(examCenterItem => getExamCenterIdentifier(examCenterItem)!);
      const examCentersToAdd = examCenters.filter(examCenterItem => {
        const examCenterIdentifier = getExamCenterIdentifier(examCenterItem);
        if (examCenterIdentifier == null || examCenterCollectionIdentifiers.includes(examCenterIdentifier)) {
          return false;
        }
        examCenterCollectionIdentifiers.push(examCenterIdentifier);
        return true;
      });
      return [...examCentersToAdd, ...examCenterCollection];
    }
    return examCenterCollection;
  }
}

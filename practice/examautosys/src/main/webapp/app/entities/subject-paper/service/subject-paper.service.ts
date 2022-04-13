import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubjectPaper, getSubjectPaperIdentifier } from '../subject-paper.model';

export type EntityResponseType = HttpResponse<ISubjectPaper>;
export type EntityArrayResponseType = HttpResponse<ISubjectPaper[]>;

@Injectable({ providedIn: 'root' })
export class SubjectPaperService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/subject-papers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(subjectPaper: ISubjectPaper): Observable<EntityResponseType> {
    return this.http.post<ISubjectPaper>(this.resourceUrl, subjectPaper, { observe: 'response' });
  }

  update(subjectPaper: ISubjectPaper): Observable<EntityResponseType> {
    return this.http.put<ISubjectPaper>(`${this.resourceUrl}/${getSubjectPaperIdentifier(subjectPaper) as number}`, subjectPaper, {
      observe: 'response',
    });
  }

  partialUpdate(subjectPaper: ISubjectPaper): Observable<EntityResponseType> {
    return this.http.patch<ISubjectPaper>(`${this.resourceUrl}/${getSubjectPaperIdentifier(subjectPaper) as number}`, subjectPaper, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubjectPaper>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubjectPaper[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSubjectPaperToCollectionIfMissing(
    subjectPaperCollection: ISubjectPaper[],
    ...subjectPapersToCheck: (ISubjectPaper | null | undefined)[]
  ): ISubjectPaper[] {
    const subjectPapers: ISubjectPaper[] = subjectPapersToCheck.filter(isPresent);
    if (subjectPapers.length > 0) {
      const subjectPaperCollectionIdentifiers = subjectPaperCollection.map(
        subjectPaperItem => getSubjectPaperIdentifier(subjectPaperItem)!
      );
      const subjectPapersToAdd = subjectPapers.filter(subjectPaperItem => {
        const subjectPaperIdentifier = getSubjectPaperIdentifier(subjectPaperItem);
        if (subjectPaperIdentifier == null || subjectPaperCollectionIdentifiers.includes(subjectPaperIdentifier)) {
          return false;
        }
        subjectPaperCollectionIdentifiers.push(subjectPaperIdentifier);
        return true;
      });
      return [...subjectPapersToAdd, ...subjectPaperCollection];
    }
    return subjectPaperCollection;
  }
}

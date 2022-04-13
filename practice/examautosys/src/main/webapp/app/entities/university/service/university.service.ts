import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUniversity, getUniversityIdentifier } from '../university.model';

export type EntityResponseType = HttpResponse<IUniversity>;
export type EntityArrayResponseType = HttpResponse<IUniversity[]>;

@Injectable({ providedIn: 'root' })
export class UniversityService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/universities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(university: IUniversity): Observable<EntityResponseType> {
    return this.http.post<IUniversity>(this.resourceUrl, university, { observe: 'response' });
  }

  update(university: IUniversity): Observable<EntityResponseType> {
    return this.http.put<IUniversity>(`${this.resourceUrl}/${getUniversityIdentifier(university) as number}`, university, {
      observe: 'response',
    });
  }

  partialUpdate(university: IUniversity): Observable<EntityResponseType> {
    return this.http.patch<IUniversity>(`${this.resourceUrl}/${getUniversityIdentifier(university) as number}`, university, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUniversity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUniversity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUniversityToCollectionIfMissing(
    universityCollection: IUniversity[],
    ...universitiesToCheck: (IUniversity | null | undefined)[]
  ): IUniversity[] {
    const universities: IUniversity[] = universitiesToCheck.filter(isPresent);
    if (universities.length > 0) {
      const universityCollectionIdentifiers = universityCollection.map(universityItem => getUniversityIdentifier(universityItem)!);
      const universitiesToAdd = universities.filter(universityItem => {
        const universityIdentifier = getUniversityIdentifier(universityItem);
        if (universityIdentifier == null || universityCollectionIdentifiers.includes(universityIdentifier)) {
          return false;
        }
        universityCollectionIdentifiers.push(universityIdentifier);
        return true;
      });
      return [...universitiesToAdd, ...universityCollection];
    }
    return universityCollection;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAcademicBatch, getAcademicBatchIdentifier } from '../academic-batch.model';

export type EntityResponseType = HttpResponse<IAcademicBatch>;
export type EntityArrayResponseType = HttpResponse<IAcademicBatch[]>;

@Injectable({ providedIn: 'root' })
export class AcademicBatchService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/academic-batches');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(academicBatch: IAcademicBatch): Observable<EntityResponseType> {
    return this.http.post<IAcademicBatch>(this.resourceUrl, academicBatch, { observe: 'response' });
  }

  update(academicBatch: IAcademicBatch): Observable<EntityResponseType> {
    return this.http.put<IAcademicBatch>(`${this.resourceUrl}/${getAcademicBatchIdentifier(academicBatch) as number}`, academicBatch, {
      observe: 'response',
    });
  }

  partialUpdate(academicBatch: IAcademicBatch): Observable<EntityResponseType> {
    return this.http.patch<IAcademicBatch>(`${this.resourceUrl}/${getAcademicBatchIdentifier(academicBatch) as number}`, academicBatch, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAcademicBatch>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAcademicBatch[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAcademicBatchToCollectionIfMissing(
    academicBatchCollection: IAcademicBatch[],
    ...academicBatchesToCheck: (IAcademicBatch | null | undefined)[]
  ): IAcademicBatch[] {
    const academicBatches: IAcademicBatch[] = academicBatchesToCheck.filter(isPresent);
    if (academicBatches.length > 0) {
      const academicBatchCollectionIdentifiers = academicBatchCollection.map(
        academicBatchItem => getAcademicBatchIdentifier(academicBatchItem)!
      );
      const academicBatchesToAdd = academicBatches.filter(academicBatchItem => {
        const academicBatchIdentifier = getAcademicBatchIdentifier(academicBatchItem);
        if (academicBatchIdentifier == null || academicBatchCollectionIdentifiers.includes(academicBatchIdentifier)) {
          return false;
        }
        academicBatchCollectionIdentifiers.push(academicBatchIdentifier);
        return true;
      });
      return [...academicBatchesToAdd, ...academicBatchCollection];
    }
    return academicBatchCollection;
  }
}

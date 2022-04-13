import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IExamApplicationForm, getExamApplicationFormIdentifier } from '../exam-application-form.model';

export type EntityResponseType = HttpResponse<IExamApplicationForm>;
export type EntityArrayResponseType = HttpResponse<IExamApplicationForm[]>;

@Injectable({ providedIn: 'root' })
export class ExamApplicationFormService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/exam-application-forms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(examApplicationForm: IExamApplicationForm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(examApplicationForm);
    return this.http
      .post<IExamApplicationForm>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(examApplicationForm: IExamApplicationForm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(examApplicationForm);
    return this.http
      .put<IExamApplicationForm>(`${this.resourceUrl}/${getExamApplicationFormIdentifier(examApplicationForm) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(examApplicationForm: IExamApplicationForm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(examApplicationForm);
    return this.http
      .patch<IExamApplicationForm>(`${this.resourceUrl}/${getExamApplicationFormIdentifier(examApplicationForm) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExamApplicationForm>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExamApplicationForm[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addExamApplicationFormToCollectionIfMissing(
    examApplicationFormCollection: IExamApplicationForm[],
    ...examApplicationFormsToCheck: (IExamApplicationForm | null | undefined)[]
  ): IExamApplicationForm[] {
    const examApplicationForms: IExamApplicationForm[] = examApplicationFormsToCheck.filter(isPresent);
    if (examApplicationForms.length > 0) {
      const examApplicationFormCollectionIdentifiers = examApplicationFormCollection.map(
        examApplicationFormItem => getExamApplicationFormIdentifier(examApplicationFormItem)!
      );
      const examApplicationFormsToAdd = examApplicationForms.filter(examApplicationFormItem => {
        const examApplicationFormIdentifier = getExamApplicationFormIdentifier(examApplicationFormItem);
        if (examApplicationFormIdentifier == null || examApplicationFormCollectionIdentifiers.includes(examApplicationFormIdentifier)) {
          return false;
        }
        examApplicationFormCollectionIdentifiers.push(examApplicationFormIdentifier);
        return true;
      });
      return [...examApplicationFormsToAdd, ...examApplicationFormCollection];
    }
    return examApplicationFormCollection;
  }

  protected convertDateFromClient(examApplicationForm: IExamApplicationForm): IExamApplicationForm {
    return Object.assign({}, examApplicationForm, {
      dob: examApplicationForm.dob?.isValid() ? examApplicationForm.dob.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dob = res.body.dob ? dayjs(res.body.dob) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((examApplicationForm: IExamApplicationForm) => {
        examApplicationForm.dob = examApplicationForm.dob ? dayjs(examApplicationForm.dob) : undefined;
      });
    }
    return res;
  }
}

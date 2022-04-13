import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IExamApplicationForm, ExamApplicationForm } from '../exam-application-form.model';
import { ExamApplicationFormService } from '../service/exam-application-form.service';

import { ExamApplicationFormRoutingResolveService } from './exam-application-form-routing-resolve.service';

describe('ExamApplicationForm routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ExamApplicationFormRoutingResolveService;
  let service: ExamApplicationFormService;
  let resultExamApplicationForm: IExamApplicationForm | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(ExamApplicationFormRoutingResolveService);
    service = TestBed.inject(ExamApplicationFormService);
    resultExamApplicationForm = undefined;
  });

  describe('resolve', () => {
    it('should return IExamApplicationForm returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultExamApplicationForm = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultExamApplicationForm).toEqual({ id: 123 });
    });

    it('should return new IExamApplicationForm if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultExamApplicationForm = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultExamApplicationForm).toEqual(new ExamApplicationForm());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ExamApplicationForm })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultExamApplicationForm = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultExamApplicationForm).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

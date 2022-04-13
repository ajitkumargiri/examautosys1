import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISubjectPaper, SubjectPaper } from '../subject-paper.model';
import { SubjectPaperService } from '../service/subject-paper.service';

import { SubjectPaperRoutingResolveService } from './subject-paper-routing-resolve.service';

describe('SubjectPaper routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SubjectPaperRoutingResolveService;
  let service: SubjectPaperService;
  let resultSubjectPaper: ISubjectPaper | undefined;

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
    routingResolveService = TestBed.inject(SubjectPaperRoutingResolveService);
    service = TestBed.inject(SubjectPaperService);
    resultSubjectPaper = undefined;
  });

  describe('resolve', () => {
    it('should return ISubjectPaper returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubjectPaper = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSubjectPaper).toEqual({ id: 123 });
    });

    it('should return new ISubjectPaper if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubjectPaper = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSubjectPaper).toEqual(new SubjectPaper());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SubjectPaper })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubjectPaper = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSubjectPaper).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

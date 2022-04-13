import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAcademicBatch, AcademicBatch } from '../academic-batch.model';
import { AcademicBatchService } from '../service/academic-batch.service';

import { AcademicBatchRoutingResolveService } from './academic-batch-routing-resolve.service';

describe('AcademicBatch routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AcademicBatchRoutingResolveService;
  let service: AcademicBatchService;
  let resultAcademicBatch: IAcademicBatch | undefined;

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
    routingResolveService = TestBed.inject(AcademicBatchRoutingResolveService);
    service = TestBed.inject(AcademicBatchService);
    resultAcademicBatch = undefined;
  });

  describe('resolve', () => {
    it('should return IAcademicBatch returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAcademicBatch = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAcademicBatch).toEqual({ id: 123 });
    });

    it('should return new IAcademicBatch if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAcademicBatch = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAcademicBatch).toEqual(new AcademicBatch());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AcademicBatch })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAcademicBatch = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAcademicBatch).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

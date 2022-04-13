import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IExamCenter, ExamCenter } from '../exam-center.model';

import { ExamCenterService } from './exam-center.service';

describe('ExamCenter Service', () => {
  let service: ExamCenterService;
  let httpMock: HttpTestingController;
  let elemDefault: IExamCenter;
  let expectedResult: IExamCenter | IExamCenter[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ExamCenterService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      code: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ExamCenter', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ExamCenter()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ExamCenter', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          code: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ExamCenter', () => {
      const patchObject = Object.assign({}, new ExamCenter());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ExamCenter', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          code: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ExamCenter', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addExamCenterToCollectionIfMissing', () => {
      it('should add a ExamCenter to an empty array', () => {
        const examCenter: IExamCenter = { id: 123 };
        expectedResult = service.addExamCenterToCollectionIfMissing([], examCenter);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(examCenter);
      });

      it('should not add a ExamCenter to an array that contains it', () => {
        const examCenter: IExamCenter = { id: 123 };
        const examCenterCollection: IExamCenter[] = [
          {
            ...examCenter,
          },
          { id: 456 },
        ];
        expectedResult = service.addExamCenterToCollectionIfMissing(examCenterCollection, examCenter);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ExamCenter to an array that doesn't contain it", () => {
        const examCenter: IExamCenter = { id: 123 };
        const examCenterCollection: IExamCenter[] = [{ id: 456 }];
        expectedResult = service.addExamCenterToCollectionIfMissing(examCenterCollection, examCenter);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(examCenter);
      });

      it('should add only unique ExamCenter to an array', () => {
        const examCenterArray: IExamCenter[] = [{ id: 123 }, { id: 456 }, { id: 26930 }];
        const examCenterCollection: IExamCenter[] = [{ id: 123 }];
        expectedResult = service.addExamCenterToCollectionIfMissing(examCenterCollection, ...examCenterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const examCenter: IExamCenter = { id: 123 };
        const examCenter2: IExamCenter = { id: 456 };
        expectedResult = service.addExamCenterToCollectionIfMissing([], examCenter, examCenter2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(examCenter);
        expect(expectedResult).toContain(examCenter2);
      });

      it('should accept null and undefined values', () => {
        const examCenter: IExamCenter = { id: 123 };
        expectedResult = service.addExamCenterToCollectionIfMissing([], null, examCenter, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(examCenter);
      });

      it('should return initial array if no ExamCenter is added', () => {
        const examCenterCollection: IExamCenter[] = [{ id: 123 }];
        expectedResult = service.addExamCenterToCollectionIfMissing(examCenterCollection, undefined, null);
        expect(expectedResult).toEqual(examCenterCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

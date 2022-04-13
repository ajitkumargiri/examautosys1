import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAcademicBatch, AcademicBatch } from '../academic-batch.model';

import { AcademicBatchService } from './academic-batch.service';

describe('AcademicBatch Service', () => {
  let service: AcademicBatchService;
  let httpMock: HttpTestingController;
  let elemDefault: IAcademicBatch;
  let expectedResult: IAcademicBatch | IAcademicBatch[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AcademicBatchService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      code: 'AAAAAAA',
      academicStartYear: 0,
      academicEndYear: 0,
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

    it('should create a AcademicBatch', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AcademicBatch()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AcademicBatch', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          code: 'BBBBBB',
          academicStartYear: 1,
          academicEndYear: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AcademicBatch', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
        },
        new AcademicBatch()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AcademicBatch', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          code: 'BBBBBB',
          academicStartYear: 1,
          academicEndYear: 1,
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

    it('should delete a AcademicBatch', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAcademicBatchToCollectionIfMissing', () => {
      it('should add a AcademicBatch to an empty array', () => {
        const academicBatch: IAcademicBatch = { id: 123 };
        expectedResult = service.addAcademicBatchToCollectionIfMissing([], academicBatch);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(academicBatch);
      });

      it('should not add a AcademicBatch to an array that contains it', () => {
        const academicBatch: IAcademicBatch = { id: 123 };
        const academicBatchCollection: IAcademicBatch[] = [
          {
            ...academicBatch,
          },
          { id: 456 },
        ];
        expectedResult = service.addAcademicBatchToCollectionIfMissing(academicBatchCollection, academicBatch);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AcademicBatch to an array that doesn't contain it", () => {
        const academicBatch: IAcademicBatch = { id: 123 };
        const academicBatchCollection: IAcademicBatch[] = [{ id: 456 }];
        expectedResult = service.addAcademicBatchToCollectionIfMissing(academicBatchCollection, academicBatch);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(academicBatch);
      });

      it('should add only unique AcademicBatch to an array', () => {
        const academicBatchArray: IAcademicBatch[] = [{ id: 123 }, { id: 456 }, { id: 10655 }];
        const academicBatchCollection: IAcademicBatch[] = [{ id: 123 }];
        expectedResult = service.addAcademicBatchToCollectionIfMissing(academicBatchCollection, ...academicBatchArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const academicBatch: IAcademicBatch = { id: 123 };
        const academicBatch2: IAcademicBatch = { id: 456 };
        expectedResult = service.addAcademicBatchToCollectionIfMissing([], academicBatch, academicBatch2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(academicBatch);
        expect(expectedResult).toContain(academicBatch2);
      });

      it('should accept null and undefined values', () => {
        const academicBatch: IAcademicBatch = { id: 123 };
        expectedResult = service.addAcademicBatchToCollectionIfMissing([], null, academicBatch, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(academicBatch);
      });

      it('should return initial array if no AcademicBatch is added', () => {
        const academicBatchCollection: IAcademicBatch[] = [{ id: 123 }];
        expectedResult = service.addAcademicBatchToCollectionIfMissing(academicBatchCollection, undefined, null);
        expect(expectedResult).toEqual(academicBatchCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

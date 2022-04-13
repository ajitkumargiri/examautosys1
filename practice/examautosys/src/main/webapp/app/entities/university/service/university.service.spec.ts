import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUniversity, University } from '../university.model';

import { UniversityService } from './university.service';

describe('University Service', () => {
  let service: UniversityService;
  let httpMock: HttpTestingController;
  let elemDefault: IUniversity;
  let expectedResult: IUniversity | IUniversity[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UniversityService);
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

    it('should create a University', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new University()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a University', () => {
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

    it('should partial update a University', () => {
      const patchObject = Object.assign({}, new University());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of University', () => {
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

    it('should delete a University', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUniversityToCollectionIfMissing', () => {
      it('should add a University to an empty array', () => {
        const university: IUniversity = { id: 123 };
        expectedResult = service.addUniversityToCollectionIfMissing([], university);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(university);
      });

      it('should not add a University to an array that contains it', () => {
        const university: IUniversity = { id: 123 };
        const universityCollection: IUniversity[] = [
          {
            ...university,
          },
          { id: 456 },
        ];
        expectedResult = service.addUniversityToCollectionIfMissing(universityCollection, university);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a University to an array that doesn't contain it", () => {
        const university: IUniversity = { id: 123 };
        const universityCollection: IUniversity[] = [{ id: 456 }];
        expectedResult = service.addUniversityToCollectionIfMissing(universityCollection, university);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(university);
      });

      it('should add only unique University to an array', () => {
        const universityArray: IUniversity[] = [{ id: 123 }, { id: 456 }, { id: 93114 }];
        const universityCollection: IUniversity[] = [{ id: 123 }];
        expectedResult = service.addUniversityToCollectionIfMissing(universityCollection, ...universityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const university: IUniversity = { id: 123 };
        const university2: IUniversity = { id: 456 };
        expectedResult = service.addUniversityToCollectionIfMissing([], university, university2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(university);
        expect(expectedResult).toContain(university2);
      });

      it('should accept null and undefined values', () => {
        const university: IUniversity = { id: 123 };
        expectedResult = service.addUniversityToCollectionIfMissing([], null, university, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(university);
      });

      it('should return initial array if no University is added', () => {
        const universityCollection: IUniversity[] = [{ id: 123 }];
        expectedResult = service.addUniversityToCollectionIfMissing(universityCollection, undefined, null);
        expect(expectedResult).toEqual(universityCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

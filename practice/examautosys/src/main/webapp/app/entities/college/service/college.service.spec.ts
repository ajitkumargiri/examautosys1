import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICollege, College } from '../college.model';

import { CollegeService } from './college.service';

describe('College Service', () => {
  let service: CollegeService;
  let httpMock: HttpTestingController;
  let elemDefault: ICollege;
  let expectedResult: ICollege | ICollege[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CollegeService);
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

    it('should create a College', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new College()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a College', () => {
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

    it('should partial update a College', () => {
      const patchObject = Object.assign({}, new College());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of College', () => {
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

    it('should delete a College', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCollegeToCollectionIfMissing', () => {
      it('should add a College to an empty array', () => {
        const college: ICollege = { id: 123 };
        expectedResult = service.addCollegeToCollectionIfMissing([], college);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(college);
      });

      it('should not add a College to an array that contains it', () => {
        const college: ICollege = { id: 123 };
        const collegeCollection: ICollege[] = [
          {
            ...college,
          },
          { id: 456 },
        ];
        expectedResult = service.addCollegeToCollectionIfMissing(collegeCollection, college);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a College to an array that doesn't contain it", () => {
        const college: ICollege = { id: 123 };
        const collegeCollection: ICollege[] = [{ id: 456 }];
        expectedResult = service.addCollegeToCollectionIfMissing(collegeCollection, college);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(college);
      });

      it('should add only unique College to an array', () => {
        const collegeArray: ICollege[] = [{ id: 123 }, { id: 456 }, { id: 81941 }];
        const collegeCollection: ICollege[] = [{ id: 123 }];
        expectedResult = service.addCollegeToCollectionIfMissing(collegeCollection, ...collegeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const college: ICollege = { id: 123 };
        const college2: ICollege = { id: 456 };
        expectedResult = service.addCollegeToCollectionIfMissing([], college, college2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(college);
        expect(expectedResult).toContain(college2);
      });

      it('should accept null and undefined values', () => {
        const college: ICollege = { id: 123 };
        expectedResult = service.addCollegeToCollectionIfMissing([], null, college, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(college);
      });

      it('should return initial array if no College is added', () => {
        const collegeCollection: ICollege[] = [{ id: 123 }];
        expectedResult = service.addCollegeToCollectionIfMissing(collegeCollection, undefined, null);
        expect(expectedResult).toEqual(collegeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

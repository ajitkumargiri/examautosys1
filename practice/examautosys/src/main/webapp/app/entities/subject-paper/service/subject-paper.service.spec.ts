import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { SubjectPaperType } from 'app/entities/enumerations/subject-paper-type.model';
import { ISubjectPaper, SubjectPaper } from '../subject-paper.model';

import { SubjectPaperService } from './subject-paper.service';

describe('SubjectPaper Service', () => {
  let service: SubjectPaperService;
  let httpMock: HttpTestingController;
  let elemDefault: ISubjectPaper;
  let expectedResult: ISubjectPaper | ISubjectPaper[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SubjectPaperService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      code: 'AAAAAAA',
      type: SubjectPaperType.PRACTICAL,
      fullMark: 0,
      passMark: 0,
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

    it('should create a SubjectPaper', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SubjectPaper()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SubjectPaper', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          code: 'BBBBBB',
          type: 'BBBBBB',
          fullMark: 1,
          passMark: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SubjectPaper', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          code: 'BBBBBB',
          type: 'BBBBBB',
          passMark: 1,
        },
        new SubjectPaper()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SubjectPaper', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          code: 'BBBBBB',
          type: 'BBBBBB',
          fullMark: 1,
          passMark: 1,
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

    it('should delete a SubjectPaper', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSubjectPaperToCollectionIfMissing', () => {
      it('should add a SubjectPaper to an empty array', () => {
        const subjectPaper: ISubjectPaper = { id: 123 };
        expectedResult = service.addSubjectPaperToCollectionIfMissing([], subjectPaper);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subjectPaper);
      });

      it('should not add a SubjectPaper to an array that contains it', () => {
        const subjectPaper: ISubjectPaper = { id: 123 };
        const subjectPaperCollection: ISubjectPaper[] = [
          {
            ...subjectPaper,
          },
          { id: 456 },
        ];
        expectedResult = service.addSubjectPaperToCollectionIfMissing(subjectPaperCollection, subjectPaper);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SubjectPaper to an array that doesn't contain it", () => {
        const subjectPaper: ISubjectPaper = { id: 123 };
        const subjectPaperCollection: ISubjectPaper[] = [{ id: 456 }];
        expectedResult = service.addSubjectPaperToCollectionIfMissing(subjectPaperCollection, subjectPaper);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subjectPaper);
      });

      it('should add only unique SubjectPaper to an array', () => {
        const subjectPaperArray: ISubjectPaper[] = [{ id: 123 }, { id: 456 }, { id: 32467 }];
        const subjectPaperCollection: ISubjectPaper[] = [{ id: 123 }];
        expectedResult = service.addSubjectPaperToCollectionIfMissing(subjectPaperCollection, ...subjectPaperArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subjectPaper: ISubjectPaper = { id: 123 };
        const subjectPaper2: ISubjectPaper = { id: 456 };
        expectedResult = service.addSubjectPaperToCollectionIfMissing([], subjectPaper, subjectPaper2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subjectPaper);
        expect(expectedResult).toContain(subjectPaper2);
      });

      it('should accept null and undefined values', () => {
        const subjectPaper: ISubjectPaper = { id: 123 };
        expectedResult = service.addSubjectPaperToCollectionIfMissing([], null, subjectPaper, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subjectPaper);
      });

      it('should return initial array if no SubjectPaper is added', () => {
        const subjectPaperCollection: ISubjectPaper[] = [{ id: 123 }];
        expectedResult = service.addSubjectPaperToCollectionIfMissing(subjectPaperCollection, undefined, null);
        expect(expectedResult).toEqual(subjectPaperCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

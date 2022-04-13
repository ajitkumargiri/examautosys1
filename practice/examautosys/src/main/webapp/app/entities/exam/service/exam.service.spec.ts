import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ExamType } from 'app/entities/enumerations/exam-type.model';
import { IExam, Exam } from '../exam.model';

import { ExamService } from './exam.service';

describe('Exam Service', () => {
  let service: ExamService;
  let httpMock: HttpTestingController;
  let elemDefault: IExam;
  let expectedResult: IExam | IExam[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ExamService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      type: ExamType.REGULAR,
      code: 'AAAAAAA',
      date: currentDate,
      startTime: currentDate,
      endTime: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
          startTime: currentDate.format(DATE_FORMAT),
          endTime: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Exam', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          date: currentDate.format(DATE_TIME_FORMAT),
          startTime: currentDate.format(DATE_FORMAT),
          endTime: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          startTime: currentDate,
          endTime: currentDate,
        },
        returnedFromService
      );

      service.create(new Exam()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Exam', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          type: 'BBBBBB',
          code: 'BBBBBB',
          date: currentDate.format(DATE_TIME_FORMAT),
          startTime: currentDate.format(DATE_FORMAT),
          endTime: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          startTime: currentDate,
          endTime: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Exam', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          type: 'BBBBBB',
          code: 'BBBBBB',
          date: currentDate.format(DATE_TIME_FORMAT),
          startTime: currentDate.format(DATE_FORMAT),
        },
        new Exam()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          date: currentDate,
          startTime: currentDate,
          endTime: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Exam', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          type: 'BBBBBB',
          code: 'BBBBBB',
          date: currentDate.format(DATE_TIME_FORMAT),
          startTime: currentDate.format(DATE_FORMAT),
          endTime: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          startTime: currentDate,
          endTime: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Exam', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addExamToCollectionIfMissing', () => {
      it('should add a Exam to an empty array', () => {
        const exam: IExam = { id: 123 };
        expectedResult = service.addExamToCollectionIfMissing([], exam);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(exam);
      });

      it('should not add a Exam to an array that contains it', () => {
        const exam: IExam = { id: 123 };
        const examCollection: IExam[] = [
          {
            ...exam,
          },
          { id: 456 },
        ];
        expectedResult = service.addExamToCollectionIfMissing(examCollection, exam);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Exam to an array that doesn't contain it", () => {
        const exam: IExam = { id: 123 };
        const examCollection: IExam[] = [{ id: 456 }];
        expectedResult = service.addExamToCollectionIfMissing(examCollection, exam);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(exam);
      });

      it('should add only unique Exam to an array', () => {
        const examArray: IExam[] = [{ id: 123 }, { id: 456 }, { id: 51378 }];
        const examCollection: IExam[] = [{ id: 123 }];
        expectedResult = service.addExamToCollectionIfMissing(examCollection, ...examArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const exam: IExam = { id: 123 };
        const exam2: IExam = { id: 456 };
        expectedResult = service.addExamToCollectionIfMissing([], exam, exam2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(exam);
        expect(expectedResult).toContain(exam2);
      });

      it('should accept null and undefined values', () => {
        const exam: IExam = { id: 123 };
        expectedResult = service.addExamToCollectionIfMissing([], null, exam, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(exam);
      });

      it('should return initial array if no Exam is added', () => {
        const examCollection: IExam[] = [{ id: 123 }];
        expectedResult = service.addExamToCollectionIfMissing(examCollection, undefined, null);
        expect(expectedResult).toEqual(examCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

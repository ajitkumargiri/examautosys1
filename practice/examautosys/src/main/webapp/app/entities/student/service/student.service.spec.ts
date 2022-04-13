import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { Gender } from 'app/entities/enumerations/gender.model';
import { MaritialStatus } from 'app/entities/enumerations/maritial-status.model';
import { BloodGroup } from 'app/entities/enumerations/blood-group.model';
import { IStudent, Student } from '../student.model';

import { StudentService } from './student.service';

describe('Student Service', () => {
  let service: StudentService;
  let httpMock: HttpTestingController;
  let elemDefault: IStudent;
  let expectedResult: IStudent | IStudent[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StudentService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      regNumber: 'AAAAAAA',
      firstName: 'AAAAAAA',
      middleName: 'AAAAAAA',
      lastName: 'AAAAAAA',
      dob: currentDate,
      fatherName: 'AAAAAAA',
      motherName: 'AAAAAAA',
      email: 'AAAAAAA',
      mobileNumber: 'AAAAAAA',
      nationality: 'AAAAAAA',
      gender: Gender.MALE,
      religion: 'AAAAAAA',
      catagory: 'AAAAAAA',
      maritialStatus: MaritialStatus.MARRIED,
      adharNumber: 'AAAAAAA',
      bloodGroup: BloodGroup.O_POS,
      fatherMobileNumber: 'AAAAAAA',
      fatherEmailId: 'AAAAAAA',
      imageContentType: 'image/png',
      image: 'AAAAAAA',
      signatureContentType: 'image/png',
      signature: 'AAAAAAA',
      adharContentType: 'image/png',
      adhar: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dob: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Student', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dob: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dob: currentDate,
        },
        returnedFromService
      );

      service.create(new Student()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Student', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          regNumber: 'BBBBBB',
          firstName: 'BBBBBB',
          middleName: 'BBBBBB',
          lastName: 'BBBBBB',
          dob: currentDate.format(DATE_TIME_FORMAT),
          fatherName: 'BBBBBB',
          motherName: 'BBBBBB',
          email: 'BBBBBB',
          mobileNumber: 'BBBBBB',
          nationality: 'BBBBBB',
          gender: 'BBBBBB',
          religion: 'BBBBBB',
          catagory: 'BBBBBB',
          maritialStatus: 'BBBBBB',
          adharNumber: 'BBBBBB',
          bloodGroup: 'BBBBBB',
          fatherMobileNumber: 'BBBBBB',
          fatherEmailId: 'BBBBBB',
          image: 'BBBBBB',
          signature: 'BBBBBB',
          adhar: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dob: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Student', () => {
      const patchObject = Object.assign(
        {
          regNumber: 'BBBBBB',
          dob: currentDate.format(DATE_TIME_FORMAT),
          fatherName: 'BBBBBB',
          nationality: 'BBBBBB',
          religion: 'BBBBBB',
          maritialStatus: 'BBBBBB',
          fatherMobileNumber: 'BBBBBB',
          image: 'BBBBBB',
          signature: 'BBBBBB',
          adhar: 'BBBBBB',
        },
        new Student()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dob: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Student', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          regNumber: 'BBBBBB',
          firstName: 'BBBBBB',
          middleName: 'BBBBBB',
          lastName: 'BBBBBB',
          dob: currentDate.format(DATE_TIME_FORMAT),
          fatherName: 'BBBBBB',
          motherName: 'BBBBBB',
          email: 'BBBBBB',
          mobileNumber: 'BBBBBB',
          nationality: 'BBBBBB',
          gender: 'BBBBBB',
          religion: 'BBBBBB',
          catagory: 'BBBBBB',
          maritialStatus: 'BBBBBB',
          adharNumber: 'BBBBBB',
          bloodGroup: 'BBBBBB',
          fatherMobileNumber: 'BBBBBB',
          fatherEmailId: 'BBBBBB',
          image: 'BBBBBB',
          signature: 'BBBBBB',
          adhar: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dob: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Student', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addStudentToCollectionIfMissing', () => {
      it('should add a Student to an empty array', () => {
        const student: IStudent = { id: 123 };
        expectedResult = service.addStudentToCollectionIfMissing([], student);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(student);
      });

      it('should not add a Student to an array that contains it', () => {
        const student: IStudent = { id: 123 };
        const studentCollection: IStudent[] = [
          {
            ...student,
          },
          { id: 456 },
        ];
        expectedResult = service.addStudentToCollectionIfMissing(studentCollection, student);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Student to an array that doesn't contain it", () => {
        const student: IStudent = { id: 123 };
        const studentCollection: IStudent[] = [{ id: 456 }];
        expectedResult = service.addStudentToCollectionIfMissing(studentCollection, student);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(student);
      });

      it('should add only unique Student to an array', () => {
        const studentArray: IStudent[] = [{ id: 123 }, { id: 456 }, { id: 78694 }];
        const studentCollection: IStudent[] = [{ id: 123 }];
        expectedResult = service.addStudentToCollectionIfMissing(studentCollection, ...studentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const student: IStudent = { id: 123 };
        const student2: IStudent = { id: 456 };
        expectedResult = service.addStudentToCollectionIfMissing([], student, student2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(student);
        expect(expectedResult).toContain(student2);
      });

      it('should accept null and undefined values', () => {
        const student: IStudent = { id: 123 };
        expectedResult = service.addStudentToCollectionIfMissing([], null, student, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(student);
      });

      it('should return initial array if no Student is added', () => {
        const studentCollection: IStudent[] = [{ id: 123 }];
        expectedResult = service.addStudentToCollectionIfMissing(studentCollection, undefined, null);
        expect(expectedResult).toEqual(studentCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

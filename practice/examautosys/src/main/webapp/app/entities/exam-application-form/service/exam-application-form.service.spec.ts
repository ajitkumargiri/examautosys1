import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { Gender } from 'app/entities/enumerations/gender.model';
import { BloodGroup } from 'app/entities/enumerations/blood-group.model';
import { IdentityType } from 'app/entities/enumerations/identity-type.model';
import { IExamApplicationForm, ExamApplicationForm } from '../exam-application-form.model';

import { ExamApplicationFormService } from './exam-application-form.service';

describe('ExamApplicationForm Service', () => {
  let service: ExamApplicationFormService;
  let httpMock: HttpTestingController;
  let elemDefault: IExamApplicationForm;
  let expectedResult: IExamApplicationForm | IExamApplicationForm[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ExamApplicationFormService);
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
      email: 'AAAAAAA',
      mobileNumber: 'AAAAAAA',
      nationality: 'AAAAAAA',
      gender: Gender.MALE,
      religion: 'AAAAAAA',
      adharNumber: 'AAAAAAA',
      bloodGroup: BloodGroup.O_POS,
      isApproved: false,
      catagory: 'AAAAAAA',
      identityType: IdentityType.ADHAR,
      identityNumber: 'AAAAAAA',
      imageContentType: 'image/png',
      image: 'AAAAAAA',
      signatureContentType: 'image/png',
      signature: 'AAAAAAA',
      adharContentType: 'image/png',
      adhar: 'AAAAAAA',
      imagePath: 'AAAAAAA',
      signPath: 'AAAAAAA',
      adharPath: 'AAAAAAA',
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

    it('should create a ExamApplicationForm', () => {
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

      service.create(new ExamApplicationForm()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ExamApplicationForm', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          regNumber: 'BBBBBB',
          firstName: 'BBBBBB',
          middleName: 'BBBBBB',
          lastName: 'BBBBBB',
          dob: currentDate.format(DATE_TIME_FORMAT),
          fatherName: 'BBBBBB',
          email: 'BBBBBB',
          mobileNumber: 'BBBBBB',
          nationality: 'BBBBBB',
          gender: 'BBBBBB',
          religion: 'BBBBBB',
          adharNumber: 'BBBBBB',
          bloodGroup: 'BBBBBB',
          isApproved: true,
          catagory: 'BBBBBB',
          identityType: 'BBBBBB',
          identityNumber: 'BBBBBB',
          image: 'BBBBBB',
          signature: 'BBBBBB',
          adhar: 'BBBBBB',
          imagePath: 'BBBBBB',
          signPath: 'BBBBBB',
          adharPath: 'BBBBBB',
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

    it('should partial update a ExamApplicationForm', () => {
      const patchObject = Object.assign(
        {
          firstName: 'BBBBBB',
          middleName: 'BBBBBB',
          email: 'BBBBBB',
          mobileNumber: 'BBBBBB',
          adharNumber: 'BBBBBB',
          bloodGroup: 'BBBBBB',
          catagory: 'BBBBBB',
          identityType: 'BBBBBB',
          identityNumber: 'BBBBBB',
          signature: 'BBBBBB',
          signPath: 'BBBBBB',
          adharPath: 'BBBBBB',
        },
        new ExamApplicationForm()
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

    it('should return a list of ExamApplicationForm', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          regNumber: 'BBBBBB',
          firstName: 'BBBBBB',
          middleName: 'BBBBBB',
          lastName: 'BBBBBB',
          dob: currentDate.format(DATE_TIME_FORMAT),
          fatherName: 'BBBBBB',
          email: 'BBBBBB',
          mobileNumber: 'BBBBBB',
          nationality: 'BBBBBB',
          gender: 'BBBBBB',
          religion: 'BBBBBB',
          adharNumber: 'BBBBBB',
          bloodGroup: 'BBBBBB',
          isApproved: true,
          catagory: 'BBBBBB',
          identityType: 'BBBBBB',
          identityNumber: 'BBBBBB',
          image: 'BBBBBB',
          signature: 'BBBBBB',
          adhar: 'BBBBBB',
          imagePath: 'BBBBBB',
          signPath: 'BBBBBB',
          adharPath: 'BBBBBB',
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

    it('should delete a ExamApplicationForm', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addExamApplicationFormToCollectionIfMissing', () => {
      it('should add a ExamApplicationForm to an empty array', () => {
        const examApplicationForm: IExamApplicationForm = { id: 123 };
        expectedResult = service.addExamApplicationFormToCollectionIfMissing([], examApplicationForm);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(examApplicationForm);
      });

      it('should not add a ExamApplicationForm to an array that contains it', () => {
        const examApplicationForm: IExamApplicationForm = { id: 123 };
        const examApplicationFormCollection: IExamApplicationForm[] = [
          {
            ...examApplicationForm,
          },
          { id: 456 },
        ];
        expectedResult = service.addExamApplicationFormToCollectionIfMissing(examApplicationFormCollection, examApplicationForm);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ExamApplicationForm to an array that doesn't contain it", () => {
        const examApplicationForm: IExamApplicationForm = { id: 123 };
        const examApplicationFormCollection: IExamApplicationForm[] = [{ id: 456 }];
        expectedResult = service.addExamApplicationFormToCollectionIfMissing(examApplicationFormCollection, examApplicationForm);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(examApplicationForm);
      });

      it('should add only unique ExamApplicationForm to an array', () => {
        const examApplicationFormArray: IExamApplicationForm[] = [{ id: 123 }, { id: 456 }, { id: 33427 }];
        const examApplicationFormCollection: IExamApplicationForm[] = [{ id: 123 }];
        expectedResult = service.addExamApplicationFormToCollectionIfMissing(examApplicationFormCollection, ...examApplicationFormArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const examApplicationForm: IExamApplicationForm = { id: 123 };
        const examApplicationForm2: IExamApplicationForm = { id: 456 };
        expectedResult = service.addExamApplicationFormToCollectionIfMissing([], examApplicationForm, examApplicationForm2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(examApplicationForm);
        expect(expectedResult).toContain(examApplicationForm2);
      });

      it('should accept null and undefined values', () => {
        const examApplicationForm: IExamApplicationForm = { id: 123 };
        expectedResult = service.addExamApplicationFormToCollectionIfMissing([], null, examApplicationForm, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(examApplicationForm);
      });

      it('should return initial array if no ExamApplicationForm is added', () => {
        const examApplicationFormCollection: IExamApplicationForm[] = [{ id: 123 }];
        expectedResult = service.addExamApplicationFormToCollectionIfMissing(examApplicationFormCollection, undefined, null);
        expect(expectedResult).toEqual(examApplicationFormCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

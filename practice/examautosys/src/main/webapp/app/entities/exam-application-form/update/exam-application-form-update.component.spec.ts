import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ExamApplicationFormService } from '../service/exam-application-form.service';
import { IExamApplicationForm, ExamApplicationForm } from '../exam-application-form.model';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IStudent } from 'app/entities/student/student.model';
import { StudentService } from 'app/entities/student/service/student.service';
import { IExam } from 'app/entities/exam/exam.model';
import { ExamService } from 'app/entities/exam/service/exam.service';
import { IExamCenter } from 'app/entities/exam-center/exam-center.model';
import { ExamCenterService } from 'app/entities/exam-center/service/exam-center.service';

import { ExamApplicationFormUpdateComponent } from './exam-application-form-update.component';

describe('ExamApplicationForm Management Update Component', () => {
  let comp: ExamApplicationFormUpdateComponent;
  let fixture: ComponentFixture<ExamApplicationFormUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let examApplicationFormService: ExamApplicationFormService;
  let addressService: AddressService;
  let studentService: StudentService;
  let examService: ExamService;
  let examCenterService: ExamCenterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ExamApplicationFormUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ExamApplicationFormUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ExamApplicationFormUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    examApplicationFormService = TestBed.inject(ExamApplicationFormService);
    addressService = TestBed.inject(AddressService);
    studentService = TestBed.inject(StudentService);
    examService = TestBed.inject(ExamService);
    examCenterService = TestBed.inject(ExamCenterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call correspondingAddress query and add missing value', () => {
      const examApplicationForm: IExamApplicationForm = { id: 456 };
      const correspondingAddress: IAddress = { id: 44266 };
      examApplicationForm.correspondingAddress = correspondingAddress;

      const correspondingAddressCollection: IAddress[] = [{ id: 61020 }];
      jest.spyOn(addressService, 'query').mockReturnValue(of(new HttpResponse({ body: correspondingAddressCollection })));
      const expectedCollection: IAddress[] = [correspondingAddress, ...correspondingAddressCollection];
      jest.spyOn(addressService, 'addAddressToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ examApplicationForm });
      comp.ngOnInit();

      expect(addressService.query).toHaveBeenCalled();
      expect(addressService.addAddressToCollectionIfMissing).toHaveBeenCalledWith(correspondingAddressCollection, correspondingAddress);
      expect(comp.correspondingAddressesCollection).toEqual(expectedCollection);
    });

    it('Should call Student query and add missing value', () => {
      const examApplicationForm: IExamApplicationForm = { id: 456 };
      const student: IStudent = { id: 14795 };
      examApplicationForm.student = student;

      const studentCollection: IStudent[] = [{ id: 35990 }];
      jest.spyOn(studentService, 'query').mockReturnValue(of(new HttpResponse({ body: studentCollection })));
      const additionalStudents = [student];
      const expectedCollection: IStudent[] = [...additionalStudents, ...studentCollection];
      jest.spyOn(studentService, 'addStudentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ examApplicationForm });
      comp.ngOnInit();

      expect(studentService.query).toHaveBeenCalled();
      expect(studentService.addStudentToCollectionIfMissing).toHaveBeenCalledWith(studentCollection, ...additionalStudents);
      expect(comp.studentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Exam query and add missing value', () => {
      const examApplicationForm: IExamApplicationForm = { id: 456 };
      const exam: IExam = { id: 38972 };
      examApplicationForm.exam = exam;

      const examCollection: IExam[] = [{ id: 75292 }];
      jest.spyOn(examService, 'query').mockReturnValue(of(new HttpResponse({ body: examCollection })));
      const additionalExams = [exam];
      const expectedCollection: IExam[] = [...additionalExams, ...examCollection];
      jest.spyOn(examService, 'addExamToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ examApplicationForm });
      comp.ngOnInit();

      expect(examService.query).toHaveBeenCalled();
      expect(examService.addExamToCollectionIfMissing).toHaveBeenCalledWith(examCollection, ...additionalExams);
      expect(comp.examsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ExamCenter query and add missing value', () => {
      const examApplicationForm: IExamApplicationForm = { id: 456 };
      const examCenter: IExamCenter = { id: 82912 };
      examApplicationForm.examCenter = examCenter;

      const examCenterCollection: IExamCenter[] = [{ id: 74166 }];
      jest.spyOn(examCenterService, 'query').mockReturnValue(of(new HttpResponse({ body: examCenterCollection })));
      const additionalExamCenters = [examCenter];
      const expectedCollection: IExamCenter[] = [...additionalExamCenters, ...examCenterCollection];
      jest.spyOn(examCenterService, 'addExamCenterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ examApplicationForm });
      comp.ngOnInit();

      expect(examCenterService.query).toHaveBeenCalled();
      expect(examCenterService.addExamCenterToCollectionIfMissing).toHaveBeenCalledWith(examCenterCollection, ...additionalExamCenters);
      expect(comp.examCentersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const examApplicationForm: IExamApplicationForm = { id: 456 };
      const correspondingAddress: IAddress = { id: 81762 };
      examApplicationForm.correspondingAddress = correspondingAddress;
      const student: IStudent = { id: 62226 };
      examApplicationForm.student = student;
      const exam: IExam = { id: 82749 };
      examApplicationForm.exam = exam;
      const examCenter: IExamCenter = { id: 34907 };
      examApplicationForm.examCenter = examCenter;

      activatedRoute.data = of({ examApplicationForm });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(examApplicationForm));
      expect(comp.correspondingAddressesCollection).toContain(correspondingAddress);
      expect(comp.studentsSharedCollection).toContain(student);
      expect(comp.examsSharedCollection).toContain(exam);
      expect(comp.examCentersSharedCollection).toContain(examCenter);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ExamApplicationForm>>();
      const examApplicationForm = { id: 123 };
      jest.spyOn(examApplicationFormService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ examApplicationForm });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: examApplicationForm }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(examApplicationFormService.update).toHaveBeenCalledWith(examApplicationForm);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ExamApplicationForm>>();
      const examApplicationForm = new ExamApplicationForm();
      jest.spyOn(examApplicationFormService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ examApplicationForm });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: examApplicationForm }));
      saveSubject.complete();

      // THEN
      expect(examApplicationFormService.create).toHaveBeenCalledWith(examApplicationForm);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ExamApplicationForm>>();
      const examApplicationForm = { id: 123 };
      jest.spyOn(examApplicationFormService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ examApplicationForm });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(examApplicationFormService.update).toHaveBeenCalledWith(examApplicationForm);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAddressById', () => {
      it('Should return tracked Address primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAddressById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackStudentById', () => {
      it('Should return tracked Student primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackStudentById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackExamById', () => {
      it('Should return tracked Exam primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackExamById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackExamCenterById', () => {
      it('Should return tracked ExamCenter primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackExamCenterById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});

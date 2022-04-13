import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ExamCenterService } from '../service/exam-center.service';
import { IExamCenter, ExamCenter } from '../exam-center.model';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IExam } from 'app/entities/exam/exam.model';
import { ExamService } from 'app/entities/exam/service/exam.service';

import { ExamCenterUpdateComponent } from './exam-center-update.component';

describe('ExamCenter Management Update Component', () => {
  let comp: ExamCenterUpdateComponent;
  let fixture: ComponentFixture<ExamCenterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let examCenterService: ExamCenterService;
  let addressService: AddressService;
  let examService: ExamService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ExamCenterUpdateComponent],
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
      .overrideTemplate(ExamCenterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ExamCenterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    examCenterService = TestBed.inject(ExamCenterService);
    addressService = TestBed.inject(AddressService);
    examService = TestBed.inject(ExamService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call address query and add missing value', () => {
      const examCenter: IExamCenter = { id: 456 };
      const address: IAddress = { id: 84368 };
      examCenter.address = address;

      const addressCollection: IAddress[] = [{ id: 6429 }];
      jest.spyOn(addressService, 'query').mockReturnValue(of(new HttpResponse({ body: addressCollection })));
      const expectedCollection: IAddress[] = [address, ...addressCollection];
      jest.spyOn(addressService, 'addAddressToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ examCenter });
      comp.ngOnInit();

      expect(addressService.query).toHaveBeenCalled();
      expect(addressService.addAddressToCollectionIfMissing).toHaveBeenCalledWith(addressCollection, address);
      expect(comp.addressesCollection).toEqual(expectedCollection);
    });

    it('Should call Exam query and add missing value', () => {
      const examCenter: IExamCenter = { id: 456 };
      const exam: IExam = { id: 42115 };
      examCenter.exam = exam;

      const examCollection: IExam[] = [{ id: 80820 }];
      jest.spyOn(examService, 'query').mockReturnValue(of(new HttpResponse({ body: examCollection })));
      const additionalExams = [exam];
      const expectedCollection: IExam[] = [...additionalExams, ...examCollection];
      jest.spyOn(examService, 'addExamToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ examCenter });
      comp.ngOnInit();

      expect(examService.query).toHaveBeenCalled();
      expect(examService.addExamToCollectionIfMissing).toHaveBeenCalledWith(examCollection, ...additionalExams);
      expect(comp.examsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const examCenter: IExamCenter = { id: 456 };
      const address: IAddress = { id: 76025 };
      examCenter.address = address;
      const exam: IExam = { id: 73961 };
      examCenter.exam = exam;

      activatedRoute.data = of({ examCenter });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(examCenter));
      expect(comp.addressesCollection).toContain(address);
      expect(comp.examsSharedCollection).toContain(exam);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ExamCenter>>();
      const examCenter = { id: 123 };
      jest.spyOn(examCenterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ examCenter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: examCenter }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(examCenterService.update).toHaveBeenCalledWith(examCenter);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ExamCenter>>();
      const examCenter = new ExamCenter();
      jest.spyOn(examCenterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ examCenter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: examCenter }));
      saveSubject.complete();

      // THEN
      expect(examCenterService.create).toHaveBeenCalledWith(examCenter);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ExamCenter>>();
      const examCenter = { id: 123 };
      jest.spyOn(examCenterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ examCenter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(examCenterService.update).toHaveBeenCalledWith(examCenter);
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

    describe('trackExamById', () => {
      it('Should return tracked Exam primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackExamById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});

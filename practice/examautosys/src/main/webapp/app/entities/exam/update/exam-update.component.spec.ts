import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ExamService } from '../service/exam.service';
import { IExam, Exam } from '../exam.model';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { ISession } from 'app/entities/session/session.model';
import { SessionService } from 'app/entities/session/service/session.service';

import { ExamUpdateComponent } from './exam-update.component';

describe('Exam Management Update Component', () => {
  let comp: ExamUpdateComponent;
  let fixture: ComponentFixture<ExamUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let examService: ExamService;
  let addressService: AddressService;
  let sessionService: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ExamUpdateComponent],
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
      .overrideTemplate(ExamUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ExamUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    examService = TestBed.inject(ExamService);
    addressService = TestBed.inject(AddressService);
    sessionService = TestBed.inject(SessionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call correspondingAddress query and add missing value', () => {
      const exam: IExam = { id: 456 };
      const correspondingAddress: IAddress = { id: 78829 };
      exam.correspondingAddress = correspondingAddress;

      const correspondingAddressCollection: IAddress[] = [{ id: 75467 }];
      jest.spyOn(addressService, 'query').mockReturnValue(of(new HttpResponse({ body: correspondingAddressCollection })));
      const expectedCollection: IAddress[] = [correspondingAddress, ...correspondingAddressCollection];
      jest.spyOn(addressService, 'addAddressToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ exam });
      comp.ngOnInit();

      expect(addressService.query).toHaveBeenCalled();
      expect(addressService.addAddressToCollectionIfMissing).toHaveBeenCalledWith(correspondingAddressCollection, correspondingAddress);
      expect(comp.correspondingAddressesCollection).toEqual(expectedCollection);
    });

    it('Should call permanentAddress query and add missing value', () => {
      const exam: IExam = { id: 456 };
      const permanentAddress: IAddress = { id: 41858 };
      exam.permanentAddress = permanentAddress;

      const permanentAddressCollection: IAddress[] = [{ id: 50591 }];
      jest.spyOn(addressService, 'query').mockReturnValue(of(new HttpResponse({ body: permanentAddressCollection })));
      const expectedCollection: IAddress[] = [permanentAddress, ...permanentAddressCollection];
      jest.spyOn(addressService, 'addAddressToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ exam });
      comp.ngOnInit();

      expect(addressService.query).toHaveBeenCalled();
      expect(addressService.addAddressToCollectionIfMissing).toHaveBeenCalledWith(permanentAddressCollection, permanentAddress);
      expect(comp.permanentAddressesCollection).toEqual(expectedCollection);
    });

    it('Should call Session query and add missing value', () => {
      const exam: IExam = { id: 456 };
      const session: ISession = { id: 66129 };
      exam.session = session;

      const sessionCollection: ISession[] = [{ id: 13536 }];
      jest.spyOn(sessionService, 'query').mockReturnValue(of(new HttpResponse({ body: sessionCollection })));
      const additionalSessions = [session];
      const expectedCollection: ISession[] = [...additionalSessions, ...sessionCollection];
      jest.spyOn(sessionService, 'addSessionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ exam });
      comp.ngOnInit();

      expect(sessionService.query).toHaveBeenCalled();
      expect(sessionService.addSessionToCollectionIfMissing).toHaveBeenCalledWith(sessionCollection, ...additionalSessions);
      expect(comp.sessionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const exam: IExam = { id: 456 };
      const correspondingAddress: IAddress = { id: 17793 };
      exam.correspondingAddress = correspondingAddress;
      const permanentAddress: IAddress = { id: 94531 };
      exam.permanentAddress = permanentAddress;
      const session: ISession = { id: 29033 };
      exam.session = session;

      activatedRoute.data = of({ exam });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(exam));
      expect(comp.correspondingAddressesCollection).toContain(correspondingAddress);
      expect(comp.permanentAddressesCollection).toContain(permanentAddress);
      expect(comp.sessionsSharedCollection).toContain(session);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Exam>>();
      const exam = { id: 123 };
      jest.spyOn(examService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ exam });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: exam }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(examService.update).toHaveBeenCalledWith(exam);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Exam>>();
      const exam = new Exam();
      jest.spyOn(examService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ exam });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: exam }));
      saveSubject.complete();

      // THEN
      expect(examService.create).toHaveBeenCalledWith(exam);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Exam>>();
      const exam = { id: 123 };
      jest.spyOn(examService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ exam });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(examService.update).toHaveBeenCalledWith(exam);
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

    describe('trackSessionById', () => {
      it('Should return tracked Session primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSessionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});

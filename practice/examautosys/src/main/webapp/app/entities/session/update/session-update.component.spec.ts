import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SessionService } from '../service/session.service';
import { ISession, Session } from '../session.model';
import { IAcademicBatch } from 'app/entities/academic-batch/academic-batch.model';
import { AcademicBatchService } from 'app/entities/academic-batch/service/academic-batch.service';

import { SessionUpdateComponent } from './session-update.component';

describe('Session Management Update Component', () => {
  let comp: SessionUpdateComponent;
  let fixture: ComponentFixture<SessionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sessionService: SessionService;
  let academicBatchService: AcademicBatchService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SessionUpdateComponent],
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
      .overrideTemplate(SessionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SessionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sessionService = TestBed.inject(SessionService);
    academicBatchService = TestBed.inject(AcademicBatchService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AcademicBatch query and add missing value', () => {
      const session: ISession = { id: 456 };
      const academicBatch: IAcademicBatch = { id: 70192 };
      session.academicBatch = academicBatch;

      const academicBatchCollection: IAcademicBatch[] = [{ id: 3218 }];
      jest.spyOn(academicBatchService, 'query').mockReturnValue(of(new HttpResponse({ body: academicBatchCollection })));
      const additionalAcademicBatches = [academicBatch];
      const expectedCollection: IAcademicBatch[] = [...additionalAcademicBatches, ...academicBatchCollection];
      jest.spyOn(academicBatchService, 'addAcademicBatchToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ session });
      comp.ngOnInit();

      expect(academicBatchService.query).toHaveBeenCalled();
      expect(academicBatchService.addAcademicBatchToCollectionIfMissing).toHaveBeenCalledWith(
        academicBatchCollection,
        ...additionalAcademicBatches
      );
      expect(comp.academicBatchesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const session: ISession = { id: 456 };
      const academicBatch: IAcademicBatch = { id: 66384 };
      session.academicBatch = academicBatch;

      activatedRoute.data = of({ session });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(session));
      expect(comp.academicBatchesSharedCollection).toContain(academicBatch);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Session>>();
      const session = { id: 123 };
      jest.spyOn(sessionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ session });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: session }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(sessionService.update).toHaveBeenCalledWith(session);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Session>>();
      const session = new Session();
      jest.spyOn(sessionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ session });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: session }));
      saveSubject.complete();

      // THEN
      expect(sessionService.create).toHaveBeenCalledWith(session);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Session>>();
      const session = { id: 123 };
      jest.spyOn(sessionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ session });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sessionService.update).toHaveBeenCalledWith(session);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAcademicBatchById', () => {
      it('Should return tracked AcademicBatch primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAcademicBatchById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});

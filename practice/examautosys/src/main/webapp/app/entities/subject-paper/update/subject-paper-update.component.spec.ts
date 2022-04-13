import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SubjectPaperService } from '../service/subject-paper.service';
import { ISubjectPaper, SubjectPaper } from '../subject-paper.model';
import { ISession } from 'app/entities/session/session.model';
import { SessionService } from 'app/entities/session/service/session.service';

import { SubjectPaperUpdateComponent } from './subject-paper-update.component';

describe('SubjectPaper Management Update Component', () => {
  let comp: SubjectPaperUpdateComponent;
  let fixture: ComponentFixture<SubjectPaperUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subjectPaperService: SubjectPaperService;
  let sessionService: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SubjectPaperUpdateComponent],
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
      .overrideTemplate(SubjectPaperUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubjectPaperUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subjectPaperService = TestBed.inject(SubjectPaperService);
    sessionService = TestBed.inject(SessionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Session query and add missing value', () => {
      const subjectPaper: ISubjectPaper = { id: 456 };
      const session: ISession = { id: 20680 };
      subjectPaper.session = session;

      const sessionCollection: ISession[] = [{ id: 28257 }];
      jest.spyOn(sessionService, 'query').mockReturnValue(of(new HttpResponse({ body: sessionCollection })));
      const additionalSessions = [session];
      const expectedCollection: ISession[] = [...additionalSessions, ...sessionCollection];
      jest.spyOn(sessionService, 'addSessionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ subjectPaper });
      comp.ngOnInit();

      expect(sessionService.query).toHaveBeenCalled();
      expect(sessionService.addSessionToCollectionIfMissing).toHaveBeenCalledWith(sessionCollection, ...additionalSessions);
      expect(comp.sessionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const subjectPaper: ISubjectPaper = { id: 456 };
      const session: ISession = { id: 25487 };
      subjectPaper.session = session;

      activatedRoute.data = of({ subjectPaper });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(subjectPaper));
      expect(comp.sessionsSharedCollection).toContain(session);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubjectPaper>>();
      const subjectPaper = { id: 123 };
      jest.spyOn(subjectPaperService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subjectPaper });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subjectPaper }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(subjectPaperService.update).toHaveBeenCalledWith(subjectPaper);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubjectPaper>>();
      const subjectPaper = new SubjectPaper();
      jest.spyOn(subjectPaperService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subjectPaper });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subjectPaper }));
      saveSubject.complete();

      // THEN
      expect(subjectPaperService.create).toHaveBeenCalledWith(subjectPaper);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubjectPaper>>();
      const subjectPaper = { id: 123 };
      jest.spyOn(subjectPaperService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subjectPaper });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subjectPaperService.update).toHaveBeenCalledWith(subjectPaper);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSessionById', () => {
      it('Should return tracked Session primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSessionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});

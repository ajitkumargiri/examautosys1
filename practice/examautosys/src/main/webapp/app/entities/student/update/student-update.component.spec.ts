import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StudentService } from '../service/student.service';
import { IStudent, Student } from '../student.model';
import { IAcademicBatch } from 'app/entities/academic-batch/academic-batch.model';
import { AcademicBatchService } from 'app/entities/academic-batch/service/academic-batch.service';

import { StudentUpdateComponent } from './student-update.component';

describe('Student Management Update Component', () => {
  let comp: StudentUpdateComponent;
  let fixture: ComponentFixture<StudentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let studentService: StudentService;
  let academicBatchService: AcademicBatchService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StudentUpdateComponent],
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
      .overrideTemplate(StudentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StudentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    studentService = TestBed.inject(StudentService);
    academicBatchService = TestBed.inject(AcademicBatchService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AcademicBatch query and add missing value', () => {
      const student: IStudent = { id: 456 };
      const academicBatch: IAcademicBatch = { id: 63565 };
      student.academicBatch = academicBatch;

      const academicBatchCollection: IAcademicBatch[] = [{ id: 98649 }];
      jest.spyOn(academicBatchService, 'query').mockReturnValue(of(new HttpResponse({ body: academicBatchCollection })));
      const additionalAcademicBatches = [academicBatch];
      const expectedCollection: IAcademicBatch[] = [...additionalAcademicBatches, ...academicBatchCollection];
      jest.spyOn(academicBatchService, 'addAcademicBatchToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ student });
      comp.ngOnInit();

      expect(academicBatchService.query).toHaveBeenCalled();
      expect(academicBatchService.addAcademicBatchToCollectionIfMissing).toHaveBeenCalledWith(
        academicBatchCollection,
        ...additionalAcademicBatches
      );
      expect(comp.academicBatchesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const student: IStudent = { id: 456 };
      const academicBatch: IAcademicBatch = { id: 24504 };
      student.academicBatch = academicBatch;

      activatedRoute.data = of({ student });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(student));
      expect(comp.academicBatchesSharedCollection).toContain(academicBatch);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Student>>();
      const student = { id: 123 };
      jest.spyOn(studentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ student });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: student }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(studentService.update).toHaveBeenCalledWith(student);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Student>>();
      const student = new Student();
      jest.spyOn(studentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ student });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: student }));
      saveSubject.complete();

      // THEN
      expect(studentService.create).toHaveBeenCalledWith(student);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Student>>();
      const student = { id: 123 };
      jest.spyOn(studentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ student });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(studentService.update).toHaveBeenCalledWith(student);
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

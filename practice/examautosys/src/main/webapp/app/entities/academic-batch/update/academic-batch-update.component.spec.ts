import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AcademicBatchService } from '../service/academic-batch.service';
import { IAcademicBatch, AcademicBatch } from '../academic-batch.model';
import { IBranch } from 'app/entities/branch/branch.model';
import { BranchService } from 'app/entities/branch/service/branch.service';

import { AcademicBatchUpdateComponent } from './academic-batch-update.component';

describe('AcademicBatch Management Update Component', () => {
  let comp: AcademicBatchUpdateComponent;
  let fixture: ComponentFixture<AcademicBatchUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let academicBatchService: AcademicBatchService;
  let branchService: BranchService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AcademicBatchUpdateComponent],
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
      .overrideTemplate(AcademicBatchUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AcademicBatchUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    academicBatchService = TestBed.inject(AcademicBatchService);
    branchService = TestBed.inject(BranchService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Branch query and add missing value', () => {
      const academicBatch: IAcademicBatch = { id: 456 };
      const branch: IBranch = { id: 18899 };
      academicBatch.branch = branch;

      const branchCollection: IBranch[] = [{ id: 21883 }];
      jest.spyOn(branchService, 'query').mockReturnValue(of(new HttpResponse({ body: branchCollection })));
      const additionalBranches = [branch];
      const expectedCollection: IBranch[] = [...additionalBranches, ...branchCollection];
      jest.spyOn(branchService, 'addBranchToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ academicBatch });
      comp.ngOnInit();

      expect(branchService.query).toHaveBeenCalled();
      expect(branchService.addBranchToCollectionIfMissing).toHaveBeenCalledWith(branchCollection, ...additionalBranches);
      expect(comp.branchesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const academicBatch: IAcademicBatch = { id: 456 };
      const branch: IBranch = { id: 78326 };
      academicBatch.branch = branch;

      activatedRoute.data = of({ academicBatch });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(academicBatch));
      expect(comp.branchesSharedCollection).toContain(branch);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AcademicBatch>>();
      const academicBatch = { id: 123 };
      jest.spyOn(academicBatchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ academicBatch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: academicBatch }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(academicBatchService.update).toHaveBeenCalledWith(academicBatch);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AcademicBatch>>();
      const academicBatch = new AcademicBatch();
      jest.spyOn(academicBatchService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ academicBatch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: academicBatch }));
      saveSubject.complete();

      // THEN
      expect(academicBatchService.create).toHaveBeenCalledWith(academicBatch);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AcademicBatch>>();
      const academicBatch = { id: 123 };
      jest.spyOn(academicBatchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ academicBatch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(academicBatchService.update).toHaveBeenCalledWith(academicBatch);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackBranchById', () => {
      it('Should return tracked Branch primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBranchById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AcademicBatchDetailComponent } from './academic-batch-detail.component';

describe('AcademicBatch Management Detail Component', () => {
  let comp: AcademicBatchDetailComponent;
  let fixture: ComponentFixture<AcademicBatchDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AcademicBatchDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ academicBatch: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AcademicBatchDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AcademicBatchDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load academicBatch on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.academicBatch).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ExamCenterDetailComponent } from './exam-center-detail.component';

describe('ExamCenter Management Detail Component', () => {
  let comp: ExamCenterDetailComponent;
  let fixture: ComponentFixture<ExamCenterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExamCenterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ examCenter: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ExamCenterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ExamCenterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load examCenter on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.examCenter).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

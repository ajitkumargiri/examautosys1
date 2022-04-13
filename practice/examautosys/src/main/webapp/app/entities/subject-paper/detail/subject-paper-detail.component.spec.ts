import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubjectPaperDetailComponent } from './subject-paper-detail.component';

describe('SubjectPaper Management Detail Component', () => {
  let comp: SubjectPaperDetailComponent;
  let fixture: ComponentFixture<SubjectPaperDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SubjectPaperDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ subjectPaper: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SubjectPaperDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SubjectPaperDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load subjectPaper on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.subjectPaper).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UniversityDetailComponent } from './university-detail.component';

describe('University Management Detail Component', () => {
  let comp: UniversityDetailComponent;
  let fixture: ComponentFixture<UniversityDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UniversityDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ university: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UniversityDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UniversityDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load university on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.university).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

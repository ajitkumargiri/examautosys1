import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ExamCenterService } from '../service/exam-center.service';

import { ExamCenterComponent } from './exam-center.component';

describe('ExamCenter Management Component', () => {
  let comp: ExamCenterComponent;
  let fixture: ComponentFixture<ExamCenterComponent>;
  let service: ExamCenterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ExamCenterComponent],
    })
      .overrideTemplate(ExamCenterComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ExamCenterComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ExamCenterService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.examCenters?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});

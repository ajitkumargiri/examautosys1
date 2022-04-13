import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { UniversityService } from '../service/university.service';

import { UniversityComponent } from './university.component';

describe('University Management Component', () => {
  let comp: UniversityComponent;
  let fixture: ComponentFixture<UniversityComponent>;
  let service: UniversityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [UniversityComponent],
    })
      .overrideTemplate(UniversityComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UniversityComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(UniversityService);

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
    expect(comp.universities?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});

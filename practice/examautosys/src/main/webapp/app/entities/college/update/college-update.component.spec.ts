import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CollegeService } from '../service/college.service';
import { ICollege, College } from '../college.model';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IUniversity } from 'app/entities/university/university.model';
import { UniversityService } from 'app/entities/university/service/university.service';

import { CollegeUpdateComponent } from './college-update.component';

describe('College Management Update Component', () => {
  let comp: CollegeUpdateComponent;
  let fixture: ComponentFixture<CollegeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let collegeService: CollegeService;
  let addressService: AddressService;
  let universityService: UniversityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CollegeUpdateComponent],
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
      .overrideTemplate(CollegeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CollegeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    collegeService = TestBed.inject(CollegeService);
    addressService = TestBed.inject(AddressService);
    universityService = TestBed.inject(UniversityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call address query and add missing value', () => {
      const college: ICollege = { id: 456 };
      const address: IAddress = { id: 81770 };
      college.address = address;

      const addressCollection: IAddress[] = [{ id: 78369 }];
      jest.spyOn(addressService, 'query').mockReturnValue(of(new HttpResponse({ body: addressCollection })));
      const expectedCollection: IAddress[] = [address, ...addressCollection];
      jest.spyOn(addressService, 'addAddressToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ college });
      comp.ngOnInit();

      expect(addressService.query).toHaveBeenCalled();
      expect(addressService.addAddressToCollectionIfMissing).toHaveBeenCalledWith(addressCollection, address);
      expect(comp.addressesCollection).toEqual(expectedCollection);
    });

    it('Should call University query and add missing value', () => {
      const college: ICollege = { id: 456 };
      const university: IUniversity = { id: 8240 };
      college.university = university;

      const universityCollection: IUniversity[] = [{ id: 32321 }];
      jest.spyOn(universityService, 'query').mockReturnValue(of(new HttpResponse({ body: universityCollection })));
      const additionalUniversities = [university];
      const expectedCollection: IUniversity[] = [...additionalUniversities, ...universityCollection];
      jest.spyOn(universityService, 'addUniversityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ college });
      comp.ngOnInit();

      expect(universityService.query).toHaveBeenCalled();
      expect(universityService.addUniversityToCollectionIfMissing).toHaveBeenCalledWith(universityCollection, ...additionalUniversities);
      expect(comp.universitiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const college: ICollege = { id: 456 };
      const address: IAddress = { id: 94975 };
      college.address = address;
      const university: IUniversity = { id: 98119 };
      college.university = university;

      activatedRoute.data = of({ college });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(college));
      expect(comp.addressesCollection).toContain(address);
      expect(comp.universitiesSharedCollection).toContain(university);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<College>>();
      const college = { id: 123 };
      jest.spyOn(collegeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ college });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: college }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(collegeService.update).toHaveBeenCalledWith(college);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<College>>();
      const college = new College();
      jest.spyOn(collegeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ college });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: college }));
      saveSubject.complete();

      // THEN
      expect(collegeService.create).toHaveBeenCalledWith(college);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<College>>();
      const college = { id: 123 };
      jest.spyOn(collegeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ college });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(collegeService.update).toHaveBeenCalledWith(college);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAddressById', () => {
      it('Should return tracked Address primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAddressById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackUniversityById', () => {
      it('Should return tracked University primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUniversityById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});

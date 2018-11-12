/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MasterLoanAuthenticationTestModule } from '../../../../test.module';
import { UserloanUpdateComponent } from 'app/entities/MasterLoanAuthentication/userloan/userloan-update.component';
import { UserloanService } from 'app/entities/MasterLoanAuthentication/userloan/userloan.service';
import { Userloan } from 'app/shared/model/MasterLoanAuthentication/userloan.model';

describe('Component Tests', () => {
  describe('Userloan Management Update Component', () => {
    let comp: UserloanUpdateComponent;
    let fixture: ComponentFixture<UserloanUpdateComponent>;
    let service: UserloanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MasterLoanAuthenticationTestModule],
        declarations: [UserloanUpdateComponent]
      })
        .overrideTemplate(UserloanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserloanUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserloanService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new Userloan(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.userloan = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );

      it(
        'Should call create service on save for new entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new Userloan();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.userloan = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );
    });
  });
});

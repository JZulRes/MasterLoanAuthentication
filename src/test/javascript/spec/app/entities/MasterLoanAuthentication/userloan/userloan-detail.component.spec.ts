/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MasterLoanAuthenticationTestModule } from '../../../../test.module';
import { UserloanDetailComponent } from 'app/entities/MasterLoanAuthentication/userloan/userloan-detail.component';
import { Userloan } from 'app/shared/model/MasterLoanAuthentication/userloan.model';

describe('Component Tests', () => {
  describe('Userloan Management Detail Component', () => {
    let comp: UserloanDetailComponent;
    let fixture: ComponentFixture<UserloanDetailComponent>;
    const route = ({ data: of({ userloan: new Userloan(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MasterLoanAuthenticationTestModule],
        declarations: [UserloanDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserloanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserloanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userloan).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

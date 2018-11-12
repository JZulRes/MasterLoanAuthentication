/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MasterLoanAuthenticationTestModule } from '../../../../test.module';
import { UserloanComponent } from 'app/entities/MasterLoanAuthentication/userloan/userloan.component';
import { UserloanService } from 'app/entities/MasterLoanAuthentication/userloan/userloan.service';
import { Userloan } from 'app/shared/model/MasterLoanAuthentication/userloan.model';

describe('Component Tests', () => {
  describe('Userloan Management Component', () => {
    let comp: UserloanComponent;
    let fixture: ComponentFixture<UserloanComponent>;
    let service: UserloanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MasterLoanAuthenticationTestModule],
        declarations: [UserloanComponent],
        providers: []
      })
        .overrideTemplate(UserloanComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserloanComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserloanService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Userloan(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userloans[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

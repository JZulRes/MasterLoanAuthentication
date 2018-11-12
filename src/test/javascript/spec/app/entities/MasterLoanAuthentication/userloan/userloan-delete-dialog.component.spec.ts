/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MasterLoanAuthenticationTestModule } from '../../../../test.module';
import { UserloanDeleteDialogComponent } from 'app/entities/MasterLoanAuthentication/userloan/userloan-delete-dialog.component';
import { UserloanService } from 'app/entities/MasterLoanAuthentication/userloan/userloan.service';

describe('Component Tests', () => {
  describe('Userloan Management Delete Component', () => {
    let comp: UserloanDeleteDialogComponent;
    let fixture: ComponentFixture<UserloanDeleteDialogComponent>;
    let service: UserloanService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MasterLoanAuthenticationTestModule],
        declarations: [UserloanDeleteDialogComponent]
      })
        .overrideTemplate(UserloanDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserloanDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserloanService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});

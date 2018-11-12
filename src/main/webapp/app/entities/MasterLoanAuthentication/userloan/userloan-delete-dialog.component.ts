import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserloan } from 'app/shared/model/MasterLoanAuthentication/userloan.model';
import { UserloanService } from './userloan.service';

@Component({
  selector: 'jhi-userloan-delete-dialog',
  templateUrl: './userloan-delete-dialog.component.html'
})
export class UserloanDeleteDialogComponent {
  userloan: IUserloan;

  constructor(private userloanService: UserloanService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.userloanService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'userloanListModification',
        content: 'Deleted an userloan'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-userloan-delete-popup',
  template: ''
})
export class UserloanDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userloan }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserloanDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.userloan = userloan;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}

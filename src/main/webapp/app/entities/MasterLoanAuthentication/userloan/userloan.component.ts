import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserloan } from 'app/shared/model/MasterLoanAuthentication/userloan.model';
import { Principal } from 'app/core';
import { UserloanService } from './userloan.service';

@Component({
  selector: 'jhi-userloan',
  templateUrl: './userloan.component.html'
})
export class UserloanComponent implements OnInit, OnDestroy {
  userloans: IUserloan[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    private userloanService: UserloanService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private activatedRoute: ActivatedRoute,
    private principal: Principal
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.userloanService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IUserloan[]>) => (this.userloans = res.body), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.userloanService.query().subscribe(
      (res: HttpResponse<IUserloan[]>) => {
        this.userloans = res.body;
        this.currentSearch = '';
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUserloans();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUserloan) {
    return item.id;
  }

  registerChangeInUserloans() {
    this.eventSubscriber = this.eventManager.subscribe('userloanListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

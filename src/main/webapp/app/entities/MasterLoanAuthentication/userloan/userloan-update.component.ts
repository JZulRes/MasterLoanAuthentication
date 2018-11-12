import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IUserloan } from 'app/shared/model/MasterLoanAuthentication/userloan.model';
import { UserloanService } from './userloan.service';

@Component({
  selector: 'jhi-userloan-update',
  templateUrl: './userloan-update.component.html'
})
export class UserloanUpdateComponent implements OnInit {
  userloan: IUserloan;
  isSaving: boolean;

  constructor(private userloanService: UserloanService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userloan }) => {
      this.userloan = userloan;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.userloan.id !== undefined) {
      this.subscribeToSaveResponse(this.userloanService.update(this.userloan));
    } else {
      this.subscribeToSaveResponse(this.userloanService.create(this.userloan));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IUserloan>>) {
    result.subscribe((res: HttpResponse<IUserloan>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserloan } from 'app/shared/model/MasterLoanAuthentication/userloan.model';

@Component({
  selector: 'jhi-userloan-detail',
  templateUrl: './userloan-detail.component.html'
})
export class UserloanDetailComponent implements OnInit {
  userloan: IUserloan;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userloan }) => {
      this.userloan = userloan;
    });
  }

  previousState() {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRealEstate } from '../real-estate.model';

@Component({
  selector: 'jhi-real-estate-detail',
  templateUrl: './real-estate-detail.component.html',
})
export class RealEstateDetailComponent implements OnInit {
  realEstate: IRealEstate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ realEstate }) => {
      this.realEstate = realEstate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

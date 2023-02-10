import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAct } from '../act.model';

@Component({
  selector: 'jhi-act-detail',
  templateUrl: './act-detail.component.html',
})
export class ActDetailComponent implements OnInit {
  act: IAct | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ act }) => {
      this.act = act;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

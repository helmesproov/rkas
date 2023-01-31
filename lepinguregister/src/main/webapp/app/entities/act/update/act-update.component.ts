import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ActFormService, ActFormGroup } from './act-form.service';
import { IAct } from '../act.model';
import { ActService } from '../service/act.service';

@Component({
  selector: 'jhi-act-update',
  templateUrl: './act-update.component.html',
})
export class ActUpdateComponent implements OnInit {
  isSaving = false;
  act: IAct | null = null;

  editForm: ActFormGroup = this.actFormService.createActFormGroup();

  constructor(protected actService: ActService, protected actFormService: ActFormService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ act }) => {
      this.act = act;
      if (act) {
        this.updateForm(act);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const act = this.actFormService.getAct(this.editForm);
    if (act.id !== null) {
      this.subscribeToSaveResponse(this.actService.update(act));
    } else {
      this.subscribeToSaveResponse(this.actService.create(act));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAct>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(act: IAct): void {
    this.act = act;
    this.actFormService.resetForm(this.editForm, act);
  }
}

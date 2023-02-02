import { Injectable, isDevMode } from '@angular/core';
import { CanActivate, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class UserRouteAccessService implements CanActivate {

  canActivate(): boolean {
    return true;
  }
}

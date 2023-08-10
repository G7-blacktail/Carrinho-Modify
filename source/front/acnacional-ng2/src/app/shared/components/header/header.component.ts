import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NavigationEnd, Router } from '@angular/router';

import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Observable } from 'rxjs';

import { AbstractComponent } from '../abstract.component';

import { LocalStorageService, Logger, SessionService } from '../../services';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent extends AbstractComponent {

  isNavbarCollapsed = true;

  logoUrl: string = 'assets/images/logo_acnacionalheader.svg';

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal, private sessionService: SessionService) {
    super(logger, localStorageService, modalService);

    const url = window.location.href;
    console.log(url);
    if (url.indexOf('isisdigital.com.br') >= 0) {
      this.logoUrl = 'assets/images/logo-menu-isis.svg';
    } else if (url.indexOf('idcertificados.com.br') >= 0) {
      // this.logoUrl = 'assets/images/logo-menu-idcertificados.svg';
    }
  }

  public login(): void {
    this.sessionService.login();
  }

  public logout(): void {
    this.sessionService.logout();
  }

  public isLoggedIn(): boolean {
    return this.sessionService.isLoggedIn();
  }

  public isAdmin(): boolean {
    return this.sessionService.isAdmin();
  }

  public isAgentePosto(): boolean {
    return this.sessionService.isAgente();
  }

  public getUsername(): string {
    return this.sessionService.getUsername();
  }

}

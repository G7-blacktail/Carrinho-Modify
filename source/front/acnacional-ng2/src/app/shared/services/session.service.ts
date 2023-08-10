import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { KeycloakService } from 'keycloak-angular';

import { Logger } from './logger';

import { Usuario } from './../models';
import { Observable, Subject } from 'rxjs';

@Injectable()
export class SessionService {

  private loggedIn: boolean;

  private usuario: Usuario;

  constructor(private logger: Logger, private router: Router, private keycloakService: KeycloakService) {
    this.keycloakService.isLoggedIn()
    .then((b) => {
      if (b) {
        this.keycloakService.loadUserProfile()
        .then((p) => {
          this.logger.debug('Login OK ' + p.email);
          this.loggedIn = true;
          this.usuario = {
            email: p.email,
            nome: p.username
          };
        });
      } else {
        this.loggedIn = false;
      }
    });
  }

  public login(commands?: string[]): void {
    this.keycloakService.login()
    .then(() => {
      this.loggedIn = true;
      this.keycloakService.loadUserProfile()
      .then((p) => {
        this.logger.debug('Login OK ' + p.email);
        this.usuario = {
          email: p.email,
          nome: p.username
        };
      });
    })
    .catch((err) => {
      this.logger.error(err);
      this.loggedIn = false;
      this.usuario = null;
    });
  }

  public logout(redirect?: string): void {
    this.logger.debug('SessionService.logout');
    let urlToRedirect = redirect;
    if (!urlToRedirect) {
      urlToRedirect = window.location.href;
      urlToRedirect = urlToRedirect.substring(0, urlToRedirect.indexOf('/ui/')) + '/ui/publico';
    }
    this.keycloakService.logout(urlToRedirect).then(() => {
      this.logger.debug('Logout OK');
      this.loggedIn = false;
      this.usuario = null;
      // window.location.href = '/arlidersis-web/ui/';
    });
  }

  public isLoggedIn(): boolean {
    return this.loggedIn;
  }

  public isAdmin(): boolean {
    return this.isUserInRole('ADMIN');
  }

  public isGerente(): boolean {
    return this.isUserInRole('GERENTE');
  }

  public isAgente(): boolean {
    if (this.isAdmin()) {
      return true;
    }
    if (this.isGerente()) {
      return true;
    }
    return this.isUserInRole('AGENTE');
  }

  public isUserInRole(role: string): boolean {
    if (this.isLoggedIn) {
      const roles = this.keycloakService.getUserRoles();
      if ((roles) && (roles.length > 0)) {
        if (roles.indexOf(role) !== -1) {
          return true;
        }
      }
    }
    return false;
  }

  public getEmail(): string {
    if (this.isLoggedIn()) {
      return this.usuario.email;
    }
    return '';
  }

  public getUsername(): string {
    if (this.isLoggedIn()) {
      return this.usuario.nome;
    }
    return '';
  }

  public getUsuario(): Usuario {
    if (this.isLoggedIn()) {
      return this.usuario;
    }
    return null;
  }

}

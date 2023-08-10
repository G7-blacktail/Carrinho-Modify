import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { KeycloakService, KeycloakAuthGuard } from 'keycloak-angular';

@Injectable()
export class AuthGuard extends KeycloakAuthGuard {
  constructor(protected router: Router, protected keycloakService: KeycloakService) {
    super(router, keycloakService);
  }

  isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    return new Promise((resolve, reject) => {
      console.log(state.url);

      if (state.url === '/') {
        console.log('AuthGuard.check => true');
        return resolve(true);
      }

      if (state.url.startsWith('/publico')) {
        console.log('AuthGuard.check => true');
        return resolve(true);
      }

      if (state.url.startsWith('/privado/voucher')) {
        console.log('AuthGuard.check => true');
        return resolve(true);
      }

      if (!this.authenticated) {
        this.keycloakService.login();
        return;
      }

      const requiredRoles = route.data.roles;
      console.log(requiredRoles);
      if (!requiredRoles || requiredRoles.length === 0) {
        return resolve(true);
      } else {
        if (!this.roles || this.roles.length === 0) {
          resolve(false);
        }
        let granted = false;
        for (const requiredRole of requiredRoles) {
          if (this.roles.indexOf(requiredRole) > -1) {
            granted = true;
            break;
          }
        }
        resolve(granted);
      }
    });
  }
}

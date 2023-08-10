import { KeycloakService } from 'keycloak-angular';

export class Keycloakfactory {

  public static initialize(keycloak: KeycloakService): () => Promise<any> {
    return (): Promise<any> => keycloak.init(
      {
        config: {
          /*url: 'https://login.certificadoranacional.com/auth',
          realm: 'acnacional',
          clientId: 'acnacional-ui'/**/
          url: 'https://login.lidersis.com.br/auth',
          realm: 'acnacional',
          clientId: 'acnacional-ui'
        },
        initOptions: {
          // onLoad: 'login-required',
          onLoad: 'check-sso',
          checkLoginIframe: false,
          checkLoginIframeInterval: 30
        },
        enableBearerInterceptor: true,
        // Da pau quanto descomenta!!!
        // bearerExcludedUrls: ['/assets', '/pub'],
        bearerPrefix: 'Bearer',
        bearerExcludedUrls: []
      }
    );
  }

}

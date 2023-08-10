import { Injectable } from '@angular/core';

import { environment } from '../../../environments/environment';

@Injectable()
export class EnvironmentService {

  constructor() { }

  public getName(): string {
    return environment.name;
  }

  public getBaseUrl(): string {
    return environment.baseUrl;
  }

  public getResourcesUrl(): string {
    return environment.resourcesUrl;
  }

  public isProduction(): boolean {
    return environment.production;
  }

}

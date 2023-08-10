import { Injectable } from '@angular/core';

import { environment } from '../../../environments/environment';

@Injectable()
export class Logger {

  constructor() { }

  public debug(message: any): void {
    if (!environment.production) {
      console.log(`DEBUG: ${message}`);
    }
  }

  public info(message: any): void {
    if (!environment.production) {
      console.log(`INFO: ${message}`);
    }
  }

  public warn(message: any): void {
    console.log(`WARN: ${message}`);
  }

  public error(message: any): void {
    console.log(`ERROR: ${message}`);
  }

}

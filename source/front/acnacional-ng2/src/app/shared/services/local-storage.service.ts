import { Injectable } from '@angular/core';

import { environment } from '../../../environments/environment';

export interface StorageItem {
  _id: string;
  _type: string;
  value: any;
}

@Injectable()
export class LocalStorageService {

  constructor() { }

  public get(name: string): any {
    const str: any = localStorage.getItem(name);
    if (str) {
      const value = JSON.parse(str);
      try {
        if ((value._id) && (value._type)) {
          const item: StorageItem = value;
          return item.value;
        }
      } catch (e) {
        //
      }
      // OLD....
      return value;
    }
    return null;
  }

  public set(name: string, value: any): void {
    if (!value) {
      localStorage.removeItem(name);
    } else {
      const item: StorageItem = {
        _id: name,
        _type: typeof value,
        value: value
      };
      const str = JSON.stringify(item);
      localStorage.setItem(name, str);
    }
  }

}



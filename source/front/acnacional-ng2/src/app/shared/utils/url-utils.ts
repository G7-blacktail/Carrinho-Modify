import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

export abstract class URLUtils {

  public static getUrl(url: string): string {
    const newUrl = url.indexOf('?') > 0 ? url.substring(0, url.indexOf('?')) : url;
    return newUrl;
  }

  public static getParams(url: string): { [k: string]: string } {
    const params: { [k: string]: string } = {};
    const urlSearchParams = new URLSearchParams(url.indexOf('?') > 0 ? url.substring(url.indexOf('?') + 1) : '');
    urlSearchParams.forEach((v, k) => {
      params[k] = v;
    });
    return params;
   }

}

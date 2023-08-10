import { Injectable } from '@angular/core';

import { NgbDateParserFormatter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

@Injectable()
export class CustomDateParserFormatter extends NgbDateParserFormatter {

  constructor() {
    super();
  }

  format(date: NgbDateStruct): string {
    if (date) {
      return this.doPad(date.day, 2) + '/' + this.doPad(date.month, 2) + '/' + this.doPad(date.year, 4);
    }
    return '';
  }

  parse(value: string): NgbDateStruct {
    if (value) {
      const dateParts = value.trim().split('/');
      return {
        year: parseInt(dateParts[2], 10),
        month: parseInt(dateParts[1], 10),
        day: parseInt(dateParts[0], 10)
      };
    }
    return null;
  }

  private doPad(val: number, size: number): string {
    let str = `${val}`;
    while (str.length < size) {
      str = '0' + str;
    }
    return str;
  }

}

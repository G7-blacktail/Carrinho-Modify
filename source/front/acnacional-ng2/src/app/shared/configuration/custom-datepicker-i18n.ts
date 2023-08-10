import { Injectable } from '@angular/core';
import { NgbDatepickerI18n, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

@Injectable()
export class CustomDatepickerI18n extends NgbDatepickerI18n {

  private datePickerOptions = {
    monthsFull: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
                 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
    monthsShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
    weekdaysFull: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado', 'Domingo'],
    weekdaysShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab', 'Dom']
  };

  constructor() {
    super();
  }

  getWeekdayShortName(weekday: number): string {
    return this.datePickerOptions.weekdaysShort[weekday];
  }
  getMonthShortName(month: number): string {
    return this.datePickerOptions.monthsShort[month - 1];
  }
  getMonthFullName(month: number): string {
    return this.datePickerOptions.monthsFull[month - 1];
  }

  getDayAriaLabel(date: NgbDateStruct): string {
    return date.day.toString();
  }

}

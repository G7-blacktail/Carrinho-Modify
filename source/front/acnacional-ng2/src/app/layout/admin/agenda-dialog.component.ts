import { Observable } from 'rxjs';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Subject } from 'rxjs';

import { AbstractComponent } from '../../shared/components';
import { HorarioPosto, Posto } from '../../shared/models';
import { LocalStorageService, Logger, HorarioPostoService } from '../../shared/services';

@Component({
  selector: 'app-agenda-dialog',
  templateUrl: './agenda-dialog.component.html',
  styleUrls: ['./agenda-dialog.component.css']
})
export class AgendaDialogComponent extends AbstractComponent {

  item: Posto;

  data: string;

  horario: string;

  horarioList: Array<HorarioPosto>;

  evento: string;

  private subject: Subject<boolean>;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    public activeModal: NgbActiveModal, private horarioPostoService: HorarioPostoService) {
    super(logger, localStorageService, modalService);
    this.subject = new Subject<boolean>();
  }

  public configure(item: Posto, data: string): Observable<boolean> {
    this.item = item;
    this.data = data;
    this.horarioPostoService.list(item.id, data, true, 0, 1000).subscribe(
      page => {
        this.horarioList = page.content;
        this.horario = this.horarioList[0].id;
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
    return this.subject.asObservable();
  }

  public onSubmit(): void {
    const item: HorarioPosto = {
      id: this.horario,
      evento: this.evento
    };
    this.horarioPostoService.save(this.item.id, item).subscribe(
      v => {
        this.subject.next(true);
      },
      response => {
        this.subject.next(false);
      },
      () => this.logger.debug('Complete')
    );
  }

}

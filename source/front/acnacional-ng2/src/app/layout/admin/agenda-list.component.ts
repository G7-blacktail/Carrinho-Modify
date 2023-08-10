import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AbstractComponent } from '../../shared/components';
import { Posto, Page } from '../../shared/models';
import { LocalStorageService, Logger, PostoService } from '../../shared/services';

@Component({
  selector: 'app-agenda-list',
  templateUrl: './agenda-list.component.html',
  styleUrls: ['./agenda-list.component.css']
})
export class AgendaListComponent extends AbstractComponent implements OnInit {

  items: Array<Posto>;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private route: ActivatedRoute, private router: Router, private postoService: PostoService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('AgendaListComponent.ngOnInit();');
    this.doList();
  }

  private doList(): void {
    const observable = this.postoService.list('', '', true, '', 0, 1000);
    observable.subscribe(
      page => {
        this.items = page.content;
        if ((this.items) && (this.items.length === 1)) {
          const item = this.items[0];
          this.router.navigate(['/admin/agenda/', item.id]);
        }
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

}

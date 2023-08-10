import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { NgbDateParserFormatter, NgbDatepickerI18n, NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { DialogComponent, HeaderComponent } from './components';

import { CustomDateParserFormatter, CustomDatepickerI18n } from './configuration';

import { EnvironmentService, Logger,
         AgendaService,
         CompraService,
         ConvenioService,
         GrupoProdutoService,
         HorarioService,
         HorarioPostoService,
         LocalStorageService,
         MunicipioService,
         PedidoService,
         PostoService,
         PreCadastroService,
         ProdutoService,
         SessionService,
         SubGrupoProdutoService,
         TipoProdutoService,
         UfService,
         UsuarioService,
         VoucherService } from './services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    // UI
    NgbModule
  ],
  declarations: [
    DialogComponent,
    HeaderComponent
  ],
  exports: [
    DialogComponent,
    HeaderComponent
  ],
  providers: [
    EnvironmentService,
    Logger,
    AgendaService,
    CompraService,
    ConvenioService,
    GrupoProdutoService,
    HorarioService,
    HorarioPostoService,
    LocalStorageService,
    MunicipioService,
    PedidoService,
    PostoService,
    PreCadastroService,
    ProdutoService,
    SessionService,
    SubGrupoProdutoService,
    TipoProdutoService,
    UfService,
    UsuarioService,
    VoucherService,
    {provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n},
    {provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter}
  ],
  entryComponents: [
    DialogComponent
  ]
})
export class SharedModule { }

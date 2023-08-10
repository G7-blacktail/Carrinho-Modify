import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { SharedModule } from '../../shared';

import { AdminRoutingModule } from './admin-routing.module';

import { AgendaDialogComponent } from './agenda-dialog.component';
import { AgendaListComponent } from './agenda-list.component';
import { AgendaViewComponent } from './agenda-view.component';
import { ConvenioEditComponent } from './convenio-edit.component';
import { ConvenioListComponent } from './convenio-list.component';
import { GrupoProdutoEditComponent } from './grupo-produto-edit.component';
import { GrupoProdutoListComponent } from './grupo-produto-list.component';
import { PedidoListComponent } from './pedido-list.component';
import { PedidoViewComponent } from './pedido-view.component';
import { PostoEditComponent } from './posto-edit.component';
import { PostoListComponent } from './posto-list.component';
import { PrecadastroDialogComponent} from './precadastro-dialog.component';
import { ProdutoEditComponent } from './produto-edit.component';
import { ProdutoListComponent } from './produto-list.component';
import { SubGrupoProdutoEditComponent } from './subgrupo-produto-edit.component';
import { SubGrupoProdutoListComponent } from './subgrupo-produto-list.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    // UI
    NgbModule,
    // App
    AdminRoutingModule,
    SharedModule
  ],
  declarations: [
    AgendaDialogComponent,
    AgendaListComponent,
    AgendaViewComponent,
    ConvenioEditComponent,
    ConvenioListComponent,
    GrupoProdutoEditComponent,
    GrupoProdutoListComponent,
    PedidoListComponent,
    PedidoViewComponent,
    PostoEditComponent,
    PostoListComponent,
    PrecadastroDialogComponent,
    ProdutoEditComponent,
    ProdutoListComponent,
    SubGrupoProdutoEditComponent,
    SubGrupoProdutoListComponent
  ],
  entryComponents: [
    AgendaDialogComponent,
    PrecadastroDialogComponent
  ]
})
export class AdminModule { }

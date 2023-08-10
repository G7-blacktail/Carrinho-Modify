import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AgendaListComponent } from './agenda-list.component';
import { AgendaViewComponent } from './agenda-view.component';
import { ConvenioListComponent } from './convenio-list.component';
import { ConvenioEditComponent } from './convenio-edit.component';
import { GrupoProdutoEditComponent } from './grupo-produto-edit.component';
import { GrupoProdutoListComponent } from './grupo-produto-list.component';
import { PedidoListComponent } from './pedido-list.component';
import { PedidoViewComponent } from './pedido-view.component';
import { PostoEditComponent } from './posto-edit.component';
import { PostoListComponent } from './posto-list.component';
import { ProdutoListComponent } from './produto-list.component';
import { ProdutoEditComponent } from './produto-edit.component';
import { SubGrupoProdutoEditComponent } from './subgrupo-produto-edit.component';
import { SubGrupoProdutoListComponent } from './subgrupo-produto-list.component';


import { AuthGuard } from './../../authguard';

const routes: Routes = [
  { path: 'agenda', component: AgendaListComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'agenda/:id', component: AgendaViewComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'convenio', component: ConvenioListComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'convenio/new', component: ConvenioEditComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'convenio/edit/:id', component: ConvenioEditComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'grupo-produto', component: GrupoProdutoListComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'grupo-produto/new', component: GrupoProdutoEditComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'grupo-produto/edit/:id', component: GrupoProdutoEditComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'pedido', component: PedidoListComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'pedido/view/:id', component: PedidoViewComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'posto', component: PostoListComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'posto/new', component: PostoEditComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'posto/edit/:id', component: PostoEditComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'posto/calendar/:id', component: AgendaViewComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'produto', component: ProdutoListComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'produto/new', component: ProdutoEditComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'produto/edit/:id', component: ProdutoEditComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'subgrupo-produto', component: SubGrupoProdutoListComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'subgrupo-produto/new', component: SubGrupoProdutoEditComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} },
  { path: 'subgrupo-produto/edit/:id', component: SubGrupoProdutoEditComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']} }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }

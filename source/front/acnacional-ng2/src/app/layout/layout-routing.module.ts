import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './layout.component';

import { AdminModule} from './admin/admin.module';
import { PrivadoModule} from './privado/privado.module';
import { PublicoModule} from './publico/publico.module';

const routes: Routes = [
  {
    path: '', component: LayoutComponent,
    children: [
      { path: 'admin', loadChildren: './admin/admin.module#AdminModule' },
      { path: 'privado', loadChildren: './privado/privado.module#PrivadoModule' },
      { path: 'publico', loadChildren: './publico/publico.module#PublicoModule' },
      { path: 'adminx', loadChildren: () => AdminModule },
      { path: 'privadox', loadChildren: () => PrivadoModule },
      { path: 'publicox', loadChildren: () => PublicoModule }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LayoutRoutingModule { }

import { Component } from '@angular/core';
import { ActivatedRoute, Params, RouterModule, Router } from '@angular/router';

import { Logger } from '../shared/services';

@Component({
  selector: 'app-not-found',
  templateUrl: './not-found.component.html',
  styleUrls: ['not-found.component.css']
})
export class NotFoundComponent {

  constructor(logger: Logger, private route: ActivatedRoute, private router: Router) {
    //
  }

}

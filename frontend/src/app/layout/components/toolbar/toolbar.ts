import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';

import { Router } from '@angular/router';

import { TokenService } from '@core/services/token.service';



@Component({
  selector: 'app-toolbar',
  standalone: true,
  imports: [
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatMenuModule
  ],
  templateUrl: './toolbar.html',
  styleUrl: './toolbar.scss'
})
export class ToolbarComponent {

  private readonly tokenService = inject(TokenService);

  private readonly router = inject(Router);

  readonly currentUser = 'Esteban Navarro';

  logout(): void {

      this.tokenService.clear();
      this.router.navigate(['/login']);

  }

}

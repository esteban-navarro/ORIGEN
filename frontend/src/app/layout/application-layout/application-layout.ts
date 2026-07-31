import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { MatSidenavModule } from '@angular/material/sidenav';
import { ToolbarComponent } from '../components/toolbar/toolbar';
import { SidebarComponent } from '../components/sidebar/sidebar';

@Component({
  selector: 'app-application-layout',
  standalone: true,
  imports: [
    RouterOutlet,
    MatSidenavModule,
    ToolbarComponent,
    SidebarComponent,
  ],
  templateUrl: './application-layout.html',
  styleUrl: './application-layout.scss',
})
export class ApplicationLayoutComponent {

}

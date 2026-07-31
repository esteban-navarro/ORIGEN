import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';

import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';

interface NavigationGroup {
  title: string;
  items: NavigationItem[];
}

interface NavigationItem {
  icon: string;
  label: string;
  route: string;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive,
    MatListModule,
    MatIconModule
  ],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.scss'
})
export class SidebarComponent {

  navigation: NavigationGroup[] = [
    {
      title: 'GENERAL',
      items: [
        {
          icon: 'dashboard',
          label: 'Dashboard',
          route: '/'
        },
        {
          icon: 'group',
          label: 'Usuarios',
          route: '/users'
        },
        {
          icon: 'admin_panel_settings',
          label: 'Roles',
          route: '/roles'
        },
        {
          icon: 'vpn_key',
          label: 'Permisos',
          route: '/permissions'
        }
      ]
    },
    {
      title: 'ADMINISTRACIÓN',
      items: [
        {
          icon: 'settings',
          label: 'Configuración',
          route: '/configuration'
        }
      ]
    },
    {
      title: 'SISTEMA',
      items: [
        {
          icon: 'info',
          label: 'Acerca de',
          route: '/about'
        }
      ]
    }
  ];

}

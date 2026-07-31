import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { FeatureCardComponent } from '../../../../shared/ui/feature-card/feature-card';

@Component({
  selector: 'app-dashboard',
  standalone: true,
    imports: [
      MatIconModule,
      MatProgressBarModule,
      FeatureCardComponent
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})
export class DashboardComponent {
}

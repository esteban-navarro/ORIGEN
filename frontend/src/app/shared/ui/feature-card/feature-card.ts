import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-feature-card',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './feature-card.html',
  styleUrl: './feature-card.scss'
})
export class FeatureCardComponent {

  icon = input.required<string>();

  title = input.required<string>();

  description = input.required<string>();

}

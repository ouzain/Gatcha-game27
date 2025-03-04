import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="not-found-container">
      <div class="not-found-content">
        <h1>404</h1>
        <h2>Page Not Found</h2>
        <p>The page you are looking for does not exist or has been moved.</p>
        <a routerLink="/" class="btn">Return to Home</a>
      </div>
    </div>
  `,
  styles: [`
    .not-found-container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 80vh;
      text-align: center;
    }
    
    .not-found-content h1 {
      font-size: 6rem;
      color: var(--primary-color);
      margin-bottom: 0;
    }
    
    .not-found-content h2 {
      margin-bottom: 20px;
    }
    
    .not-found-content p {
      margin-bottom: 30px;
      max-width: 500px;
    }
  `]
})
export class NotFoundComponent {}
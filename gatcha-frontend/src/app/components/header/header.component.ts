import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  template: `
    <header class="header">
      <div class="container">
        <div class="header-content">
          <div class="logo">
            <a routerLink="/">Gacha Game</a>
          </div>
          <nav class="nav">
            <ul class="nav-list">
              <li *ngIf="isAuthenticated$ | async">
                <a routerLink="/profile" routerLinkActive="active">Profile</a>
              </li>
              <li *ngIf="isAuthenticated$ | async">
                <a routerLink="/monsters" routerLinkActive="active">Monsters</a>
              </li>
              <li *ngIf="isAuthenticated$ | async">
                <a routerLink="/summon" routerLinkActive="active">Summon</a>
              </li>
              <li *ngIf="isAuthenticated$ | async">
                <a routerLink="/battles" routerLinkActive="active">Battles</a>
              </li>
              <li *ngIf="!(isAuthenticated$ | async)">
                <a routerLink="/login" routerLinkActive="active">Login</a>
              </li>
              <li *ngIf="!(isAuthenticated$ | async)">
                <a routerLink="/register" routerLinkActive="active">Register</a>
              </li>
              <li *ngIf="isAuthenticated$ | async">
                <a href="#" (click)="logout($event)">Logout</a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </header>
  `,
  styles: [`
    .header {
      background-color: var(--surface-color);
      padding: 15px 0;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }
    
    .header-content {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    
    .logo a {
      color: var(--primary-color);
      font-size: 24px;
      font-weight: 700;
      text-decoration: none;
    }
    
    .nav-list {
      display: flex;
      list-style: none;
      gap: 20px;
    }
    
    .nav-list a {
      color: var(--text-color);
      text-decoration: none;
      font-weight: 500;
      transition: color 0.3s;
    }
    
    .nav-list a:hover, .nav-list a.active {
      color: var(--primary-color);
    }
    
    @media (max-width: 768px) {
      .header-content {
        flex-direction: column;
        gap: 15px;
      }
      
      .nav-list {
        flex-wrap: wrap;
        justify-content: center;
      }
    }
  `]
})
export class HeaderComponent {
  isAuthenticated$ = this.authService.isAuthenticated$;
  
  constructor(private authService: AuthService) {}
  
  logout(event: Event): void {
    event.preventDefault();
    this.authService.logout();
  }
}
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="home">
      <div class="hero">
        <h1>Bienvenue dans Gacha Game </h1>
        <p>Collectionnez des monstres, combattez des adversaires et devenez le maître ultime !</p>
        <div class="cta-buttons">
          <a *ngIf="!(isAuthenticated$ | async)" routerLink="/register" class="btn">Commencer</a>
          <a *ngIf="isAuthenticated$ | async" routerLink="/summon" class="btn">Invoquer des monstres</a>
          <a *ngIf="isAuthenticated$ | async" routerLink="/battles" class="btn btn-secondary">Commencer le combat</a>
        </div>
      </div>
      
      <div class="features">
        <div class="card feature">
          <h2>Invoquer des monstres</h2>
          <p>Invoquez et collectionnez des monstres uniques avec différents types élémentaires et        capacités.</p>
        </div>
        
        <div class="card feature">
          <h2>Monter en niveau </h2>
          <p>Entraînez vos monstres, améliorez leurs statistiques et améliorez leurs compétences pour les rendre plus forts.</p>
        </div>
        
        <div class="card feature">
          <h2>Combat</h2>
          <p>Affrontez d'autres monstres dans des batailles passionnantes et regardez l'action se dérouler.</p>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .home {
      padding: 20px 0;
    }
    
    .hero {
      text-align: center;
      padding: 60px 20px;
      margin-bottom: 40px;
    }
    
    .hero h1 {
      font-size: 3rem;
      margin-bottom: 20px;
      color: var(--primary-color);
    }
    
    .hero p {
      font-size: 1.2rem;
      margin-bottom: 30px;
      max-width: 600px;
      margin-left: auto;
      margin-right: auto;
    }
    
    .cta-buttons {
      display: flex;
      gap: 15px;
      justify-content: center;
    }
    
    .features {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
      gap: 30px;
      margin-bottom: 40px;
    }
    
    .feature {
      text-align: center;
      padding: 30px;
    }
    
    .feature h2 {
      color: var(--primary-color);
      margin-bottom: 15px;
    }
    
    @media (max-width: 768px) {
      .hero h1 {
        font-size: 2rem;
      }
      
      .cta-buttons {
        flex-direction: column;
        align-items: center;
      }
    }
  `]
})
export class HomeComponent {
  isAuthenticated$ = this.authService.isAuthenticated$;
  
  constructor(private authService: AuthService) {}
}
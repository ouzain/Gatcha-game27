import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MonsterService } from '../../services/monster.service';
import { Monster } from '../../models/monster.model';

@Component({
  selector: 'app-monsters',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="monsters-container">
      <h2 class="text-center">Your Monsters</h2>
      
      <div *ngIf="isLoading" class="text-center">
        <p>Loading monsters...</p>
      </div>
      
      <div *ngIf="!isLoading && monsters.length === 0" class="text-center">
        <p>You don't have any monsters yet.</p>
        <a routerLink="/summon" class="btn mt-3">Summon Your First Monster</a>
      </div>
      
      <div *ngIf="!isLoading && monsters.length > 0" class="monster-grid">
        <div *ngFor="let monster of monsters" class="card monster-card">
          <div class="monster-header" [ngClass]="monster.elementType">
            <h3>{{ monster.name }}</h3>
            <span class="element-badge">{{ monster.elementType | titlecase }}</span>
          </div>
          
          <div class="monster-stats">
            <div class="stat">
              <span class="stat-label">Level:</span>
              <span class="stat-value">{{ monster.level }}</span>
            </div>
            <div class="stat">
              <span class="stat-label">HP:</span>
              <span class="stat-value">{{ monster.hp }}</span>
            </div>
            <div class="stat">
              <span class="stat-label">ATK:</span>
              <span class="stat-value">{{ monster.atk }}</span>
            </div>
            <div class="stat">
              <span class="stat-label">DEF:</span>
              <span class="stat-value">{{ monster.def }}</span>
            </div>
            <div class="stat">
              <span class="stat-label">VIT:</span>
              <span class="stat-value">{{ monster.vit }}</span>
            </div>
          </div>
          
          <div class="monster-actions">
            <a [routerLink]="['/monsters', monster.id]" class="btn">View Details</a>
          </div>
        </div>
      </div>
      
      <div *ngIf="errorMessage" class="error-message text-center mt-3">
        {{ errorMessage }}
      </div>
    </div>
  `,
  styles: [`
    .monsters-container {
      padding: 20px 0;
    }
    
    .monster-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
      gap: 20px;
      margin-top: 20px;
    }
    
    .monster-card {
      display: flex;
      flex-direction: column;
      transition: transform 0.3s ease;
    }
    
    .monster-card:hover {
      transform: translateY(-5px);
    }
    
    .monster-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding-bottom: 10px;
      margin-bottom: 15px;
      border-bottom: 2px solid #333;
    }
    
    .monster-header.fire {
      border-color: var(--fire-color);
    }
    
    .monster-header.water {
      border-color: var(--water-color);
    }
    
    .monster-header.wind {
      border-color: var(--wind-color);
    }
    
    .element-badge {
      padding: 3px 8px;
      border-radius: 4px;
      font-size: 0.8rem;
      font-weight: 600;
    }
    
    .fire .element-badge {
      background-color: rgba(255, 87, 34, 0.2);
      color: var(--fire-color);
    }
    
    .water .element-badge {
      background-color: rgba(33, 150, 243, 0.2);
      color: var(--water-color);
    }
    
    .wind .element-badge {
      background-color: rgba(76, 175, 80, 0.2);
      color: var(--wind-color);
    }
    
    .monster-stats {
      flex: 1;
    }
    
    .stat {
      display: flex;
      justify-content: space-between;
      margin-bottom: 8px;
    }
    
    .stat-label {
      font-weight: 500;
    }
    
    .monster-actions {
      margin-top: 15px;
      text-align: center;
    }
  `]
})
export class MonstersComponent implements OnInit {
  monsters: Monster[] = [];
  isLoading = true;
  errorMessage = '';
  
  constructor(private monsterService: MonsterService) {}
  
  ngOnInit(): void {
    this.loadMonsters();
  }
  
  loadMonsters(): void {
    this.isLoading = true;
    
    this.monsterService.getMonsters().subscribe({
      next: (monsters) => {
        this.monsters = monsters;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load monsters. Please try again.';
        this.isLoading = false;
        console.error('Error loading monsters:', error);
      }
    });
  }
}
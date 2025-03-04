import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, ActivatedRoute } from '@angular/router';
import { SummonService } from '../../services/summon.service';
import { PlayerService } from '../../services/player.service';
import { Monster } from '../../models/monster.model';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-summon',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="invocation-container">
      <div class="card invocation-card">
        <h2 class="text-center">Invocation de monstre</h2>
        
        <div *ngIf="isLoadingProfile" class="text-center">
          <p>Loading profile...</p>
        </div>
        
        <div *ngIf="!isLoadingProfile && user">
          <div class="invocation-info">
            <p>You have {{ user.monsterIds.length }} / {{ user.maxMonsters }} monsters.</p>
            
            <div *ngIf="user.monsterIds.length >= user.maxMonsters" class="error-message text-center">
              <p>Your monster storage is full! Release some monsters or level up to increase your capacity.</p>
            </div>
          </div>
          
          <div class="invocation-action">
            <button 
              class="btn invocation-btn" 
              (click)="summonMonster()" 
              [disabled]="isSummoning || user.monsterIds.length >= user.maxMonsters"
            >
              {{ isSummoning ? 'Summoning...' : 'Summon Monster' }}
            </button>
          </div>
        </div>
        
        <div *ngIf="summonedMonster" class="summoned-monster">
          <h3 class="text-center">You summoned:</h3>
          
          <div class="card monster-card">
            <div class="monster-header" [ngClass]="summonedMonster.elementType">
              <h3>{{ summonedMonster.name }}</h3>
              <span class="element-badge">{{ summonedMonster.elementType | titlecase }}</span>
            </div>
            
            <div class="monster-stats">
              <div class="stat">
                <span class="stat-label">HP:</span>
                <span class="stat-value">{{ summonedMonster.hp }}</span>
              </div>
              <div class="stat">
                <span class="stat-label">ATK:</span>
                <span class="stat-value">{{ summonedMonster.atk }}</span>
              </div>
              <div class="stat">
                <span class="stat-label">DEF:</span>
                <span class="stat-value">{{ summonedMonster.def }}</span>
              </div>
              <div class="stat">
                <span class="stat-label">VIT:</span>
                <span class="stat-value">{{ summonedMonster.vit }}</span>
              </div>
            </div>
            
            <div class="monster-actions">
              <a [routerLink]="['/monsters', summonedMonster.id]" class="btn">View Details</a>
            </div>
          </div>
        </div>
        
        <div *ngIf="errorMessage" class="error-message text-center mt-3">
          {{ errorMessage }}
        </div>
      </div>
      
      <div class="card summon-history-card">
        <h3 class="text-center">Recent Summons</h3>
        
        <div *ngIf="isLoadingHistory" class="text-center">
          <p>Loading summon history...</p>
        </div>
        
        <div *ngIf="!isLoadingHistory && summonHistory.length === 0" class="text-center">
          <p>No recent summons.</p>
        </div>
        
        <div *ngIf="!isLoadingHistory && summonHistory.length > 0" class="summon-history">
          <div *ngFor="let monster of summonHistory" class="history-item">
            <div class="history-monster" [ngClass]="monster.elementType">
              <span class="monster-name">{{ monster.name }}</span>
              <span class="element-badge small">{{ monster.elementType | titlecase }}</span>
            </div>
            <a [routerLink]="['/monsters', monster.id]" class="btn btn-small">View</a>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .invocation-container {
      display: grid;
      grid-template-columns: 1fr;
      gap: 20px;
      padding: 20px 0;
    }
    
    @media (min-width: 768px) {
      .invocation-container {
        grid-template-columns: 2fr 1fr;
      }
    }
    
    .invocation-info {
      text-align: center;
      margin-bottom: 20px;
    }
    
    .invocation-action {
      display: flex;
      justify-content: center;
      margin-bottom: 30px;
    }
    
    .invocation-btn {
      padding: 15px 30px;
      font-size: 1.2rem;
    }
    
    .summoned-monster {
      margin-top: 30px;
    }
    
    .summoned-monster h3 {
      margin-bottom: 15px;
    }
    
    .monster-card {
      max-width: 400px;
      margin: 0 auto;
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
    
    .element-badge.small {
      padding: 2px 6px;
      font-size: 0.7rem;
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
      margin-bottom: 15px;
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
      text-align: center;
    }
    
    .summon-history {
      max-height: 400px;
      overflow-y: auto;
    }
    
    .history-item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 10px;
      margin-bottom: 10px;
      background-color: rgba(255, 255, 255, 0.05);
      border-radius: 4px;
    }
    
    .history-monster {
      display: flex;
      align-items: center;
      gap: 10px;
    }
    
    .monster-name {
      font-weight: 500;
    }
    
    .btn-small {
      padding: 5px 10px;
      font-size: 0.8rem;
    }
  `]
})
export class SummonComponent implements OnInit {
  user: User | null = null;
  summonedMonster: Monster | null = null;
  summonHistory: Monster[] = [];
  isLoadingProfile = true;
  isLoadingHistory = true;
  isSummoning = false;
  errorMessage = '';
  
  constructor(
    private summonService: SummonService,
    private playerService: PlayerService,
    private route : ActivatedRoute
  ) {}
  
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const username = params['username'];

      if (username) {
        this.loadProfile(username);
      } else {
        this.errorMessage = 'No username provided.';
      }
    });

    this.loadSummonHistory();
  }
  
  loadProfile(username: string): void {
    this.isLoadingProfile = true;
    
    this.playerService.getProfile(username).subscribe({
      next: (user) => {
        this.user = user;
        this.isLoadingProfile = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load profile. Please try again.';
        this.isLoadingProfile = false;
        console.error('Error loading profile:', error);
      }
    });
  }
  
  loadSummonHistory(): void {
    this.isLoadingHistory = true;
    
    this.summonService.getSummonHistory().subscribe({
      next: (monsters) => {
        this.summonHistory = monsters;
        this.isLoadingHistory = false;
      },
      error: (error) => {
        console.error('Error loading summon history:', error);
        this.isLoadingHistory = false;
      }
    });
  }
  
  summonMonster(): void {
    if (!this.user || this.user.monsterIds.length >= this.user.maxMonsters) return;
    
    this.isSummoning = true;
    this.summonedMonster = null;
    
    this.summonService.summonMonster().subscribe({
      next: (monster) => {
        this.summonedMonster = monster;
        this.isSummoning = false;

        // ✅ Mettre à jour le profil après invocation
        this.loadProfile(this.user!.username);

        // ✅ Mettre à jour l'historique des invocations
        this.loadSummonHistory();
      },
      error: (error) => {
        this.errorMessage = 'Failed to summon monster. Please try again.';
        this.isSummoning = false;
        console.error('Error summoning monster:', error);
      }
    });
  }
}
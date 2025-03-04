import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink,RouterModule } from '@angular/router';

import { BattleService } from '../../services/battle.service';
import { BattleWithMonsters, BattleAction } from '../../models/battle.model';

@Component({
  selector: 'app-battle-detail',
  standalone: true,
  imports: [CommonModule, RouterLink,RouterModule],
  template: `
    <div class="battle-detail-container">
      <div *ngIf="isLoading" class="text-center">
        <p>Loading battle replay...</p>
      </div>
      
      <div *ngIf="!isLoading && battle" class="battle-detail">
        <div class="card battle-info-card">
          <h2 class="text-center">Battle Replay</h2>
          
          <div class="battle-header">
            <div class="battle-monsters">
              <div class="monster-card" *ngIf="battle.monster1" [ngClass]="battle.monster1.elementType">
                <h3>{{ battle.monster1.name }}</h3>
                <div class="monster-type">{{ battle.monster1.elementType | titlecase }}</div>
                <div class="monster-level">Level {{ battle.monster1.level }}</div>
                <div class="monster-stats">
                  <div class="stat">HP: {{ battle.monster1.hp }}</div>
                  <div class="stat">ATK: {{ battle.monster1.atk }}</div>
                  <div class="stat">DEF: {{ battle.monster1.def }}</div>
                  <div class="stat">VIT: {{ battle.monster1.vit }}</div>
                </div>
              </div>
              
              <div class="vs-container">
                <div class="vs">VS</div>
              </div>
              
              <div class="monster-card" *ngIf="battle.monster2" [ngClass]="battle.monster2.elementType">
                <h3>{{ battle.monster2.name }}</h3>
                <div class="monster-type">{{ battle.monster2.elementType | titlecase }}</div>
                <div class="monster-level">Level {{ battle.monster2.level }}</div>
                <div class="monster-stats">
                  <div class="stat">HP: {{ battle.monster2.hp }}</div>
                  <div class="stat">ATK: {{ battle.monster2.atk }}</div>
                  <div class="stat">DEF: {{ battle.monster2.def }}</div>
                  <div class="stat">VIT: {{ battle.monster2.vit }}</div>
                </div>
              </div>
            </div>
            
            <div class="battle-result">
              <div class="winner">
                <span class="winner-label">Winner:</span>
                <span class="winner-name" *ngIf="battle.winner" [ngClass]="battle.winner.elementType">
                  {{ battle.winner.name }}
                </span>
              </div>
              <div class="battle-date">{{ battle.date | date:'medium' }}</div>
            </div>
          </div>
          
          <div class="battle-actions">
            <button class="btn" (click)="replayBattle()" [disabled]="isReplaying">
              {{ isReplaying ? 'Replaying...' : 'Replay Battle' }}
            </button>
          </div>
        </div>
        
        <div class="card battle-log-card">
          <h3 class="text-center">Battle Log</h3>
          
          <div class="battle-log">
            <div *ngFor="let action of battle.actions" class="log-entry">
              <div class="turn-number">Turn {{ action.turn }}</div>
              <div class="action-description">
              <span class="monster-name" [ngClass]="getMonsterElement(action.monsterId)">
                {{ getMonsterName(action.monsterId) }}
              </span>


                used 
                <span class="skill-name">{{ getSkillName(action.monsterId, action.skillId) }}</span>
                and dealt 
                <span class="damage">{{ action.damage }}</span> 
                damage.
                <span class="target-hp">
                  Target HP: {{ action.targetHp }}
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <div *ngIf="errorMessage" class="error-message text-center mt-3">
        {{ errorMessage }}
      </div>
      
      <div class="back-link">
        <a routerLink="/battles" class="btn btn-secondary">Back to Battles</a>
      </div>
    </div>
  `,
  styles: [`
    .battle-detail-container {
      padding: 20px 0;
    }
    
    .battle-detail {
      display: grid;
      grid-template-columns: 1fr;
      gap: 20px;
    }
    
    @media (min-width: 768px) {
      .battle-detail {
        grid-template-columns: 1fr 1fr;
      }
    }
    
    .battle-header {
      margin-bottom: 20px;
    }
    
    .battle-monsters {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
    }
    
    .monster-card {
      flex: 1;
      padding: 15px;
      border-radius: 4px;
      text-align: center;
    }
    
    .monster-card.fire {
      background-color: rgba(255, 87, 34, 0.1);
      border: 1px solid var(--fire-color);
    }
    
    .monster-card.water {
      background-color: rgba(33, 150, 243, 0.1);
      border: 1px solid var(--water-color);
    }
    
    .monster-card.wind {
      background-color: rgba(76, 175, 80, 0.1);
      border: 1px solid var(--wind-color);
    }
    
    .monster-card h3 {
      margin-bottom: 10px;
    }
    
    .monster-type {
      font-weight: 600;
      margin-bottom: 5px;
    }
    
    .monster-type.fire {
      color: var(--fire-color);
    }
    
    .monster-type.water {
      color: var(--water-color);
    }
    
    .monster-type.wind {
      color: var(--wind-color);
    }
    
    .monster-level {
      margin-bottom: 10px;
    }
    
    .monster-stats {
      text-align: left;
    }
    
    .stat {
      margin-bottom: 5px;
    }
    
    .vs-container {
      padding: 0 15px;
    }
    
    .vs {
      font-size: 1.5rem;
      font-weight: 700;
      color: var(--secondary-color);
    }
    
    .battle-result {
      text-align: center;
      padding: 15px;
      background-color: rgba(255, 255, 255, 0.05);
      border-radius: 4px;
    }
    
    .winner {
      margin-bottom: 10px;
    }
    
    .winner-label {
      font-size: 1.1rem;
      margin-right: 10px;
    }
    
    .winner-name {
      font-size: 1.2rem;
      font-weight: 700;
    }
    
    .winner-name.fire {
      color: var(--fire-color);
    }
    
    .winner-name.water {
      color: var(--water-color);
    }
    
    .winner-name.wind {
      color: var(--wind-color);
    }
    
    .battle-date {
      font-size: 0.9rem;
      color: #aaa;
    }
    
    .battle-actions {
      text-align: center;
      margin-top: 20px;
    }
    
    .battle-log {
      max-height: 400px;
      overflow-y: auto;
      padding: 10px;
      background-color: rgba(0, 0, 0, 0.2);
      border-radius: 4px;
    }
    
    .log-entry {
      margin-bottom: 15px;
      padding-bottom: 15px;
      border-bottom: 1px solid #333;
    }
    
    .log-entry:last-child {
      border-bottom: none;
    }
    
    .turn-number {
      font-weight: 600;
      margin-bottom: 5px;
      color: var(--secondary-color);
    }
    
    .action-description {
      line-height: 1.5;
    }
    
    .monster-name {
      font-weight: 600;
    }
    
    .monster-name.fire {
      color: var(--fire-color);
    }
    
    .monster-name.water {
      color: var(--water-color);
    }
    
    .monster-name.wind {
      color: var(--wind-color);
    }
    
    .skill-name {
      font-style: italic;
    }
    
    .damage {
      font-weight: 600;
      color: var(--error-color);
    }
    
    .target-hp {
      display: block;
      font-size: 0.9rem;
      margin-top: 5px;
      color: #aaa;
    }
    
    .back-link {
      margin-top: 20px;
      text-align: center;
    }
  `]
})
export class BattleDetailComponent implements OnInit {
  battle: BattleWithMonsters | null = null;
  isLoading = true;
  isReplaying = false;
  errorMessage = '';
  
  constructor(
    private route: ActivatedRoute,
    private battleService: BattleService
  ) {}
  
  ngOnInit(): void {
    this.loadBattle();
  }
  
  loadBattle(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      this.errorMessage = 'Battle ID not found';
      this.isLoading = false;
      return;
    }
    
    this.battleService.getBattle(id).subscribe({
      next: (battle) => {
        this.battle = battle;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load battle details. Please try again.';
        this.isLoading = false;
        console.error('Error loading battle:', error);
      }
    });
  }
  
  replayBattle(): void {
    if (!this.battle) return;
    
    this.isReplaying = true;
    
    this.battleService.replayBattle(this.battle.id).subscribe({
      next: (battle) => {
        this.battle = battle;
        this.isReplaying = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to replay battle. Please try again.';
        this.isReplaying = false;
        console.error('Error replaying battle:', error);
      }
    });
  }
  
  getMonsterName(monsterId: string): string {
    if (!this.battle) return '';
    
    if (this.battle.monster1?.id === monsterId) {
      return this.battle.monster1.name;
    } else if (this.battle.monster2?.id === monsterId) {
      return this.battle.monster2.name;
    }
    
    return '';
  }
  
  getMonsterElement(monsterId: string): string {
    if (!this.battle) return '';
    
    if (this.battle.monster1?.id === monsterId) {
      return this.battle.monster1.elementType;
    } else if (this.battle.monster2?.id === monsterId) {
      return this.battle.monster2.elementType;
    }
    
    return '';
  }
  
  getSkillName(monsterId: string, skillId: string): string {
    if (!this.battle) return '';
    
    let monster = null;
    if (this.battle.monster1?.id === monsterId) {
      monster = this.battle.monster1;
    } else if (this.battle.monster2?.id === monsterId) {
      monster = this.battle.monster2;
    }
    
    if (monster) {
      const skill = monster.skills.find(s => s.id === skillId);
      return skill ? skill.name : '';
    }
    
    return '';
  }
}
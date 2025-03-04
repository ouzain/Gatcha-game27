import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { BattleService } from '../../services/battle.service';
import { MonsterService } from '../../services/monster.service';
import { BattleWithMonsters } from '../../models/battle.model';
import { Monster } from '../../models/monster.model';

@Component({
  selector: 'app-battles',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule],
  template: `
    <div class="battles-container">
      <div class="card battle-form-card">
        <h2 class="text-center">Start a Battle</h2>
        
        <div *ngIf="isLoadingMonsters" class="text-center">
          <p>Loading your monsters...</p>
        </div>
        
        <div *ngIf="!isLoadingMonsters && monsters.length < 2" class="text-center">
          <p>You need at least 2 monsters to start a battle.</p>
          <a routerLink="/summon" class="btn mt-3">Summon More Monsters</a>
        </div>
        
        <form *ngIf="!isLoadingMonsters && monsters.length >= 2" [formGroup]="battleForm" (ngSubmit)="startBattle()">
          <div class="form-group">
            <label for="monster1">First Monster</label>
            <select id="monster1" formControlName="monster1Id" class="form-control">
              <option value="">Select a monster</option>
              <option *ngFor="let monster of monsters" [value]="monster.id">
                {{ monster.name }} (Lv. {{ monster.level }}, {{ monster.elementType | titlecase }})
              </option>
            </select>
          </div>
          
          <div class="form-group">
            <label for="monster2">Second Monster</label>
            <select id="monster2" formControlName="monster2Id" class="form-control">
              <option value="">Select a monster</option>
              <option *ngFor="let monster of monsters" [value]="monster.id">
                {{ monster.name }} (Lv. {{ monster.level }}, {{ monster.elementType | titlecase }})
              </option>
            </select>
            <div *ngIf="battleForm.errors?.['sameMonster']" class="error-message">
              You cannot select the same monster twice
            </div>
          </div>
          
          <button type="submit" class="btn w-100" [disabled]="battleForm.invalid || isStartingBattle">
            {{ isStartingBattle ? 'Starting Battle...' : 'Start Battle' }}
          </button>
        </form>
      </div>
      
      <div class="card battle-history-card">
        <h2 class="text-center">Battle History</h2>
        
        <div *ngIf="isLoadingBattles" class="text-center">
          <p>Loading battle history...</p>
        </div>
        
        <div *ngIf="!isLoadingBattles && battles.length === 0" class="text-center">
          <p>No battles found. Start your first battle!</p>
        </div>
        
        <div *ngIf="!isLoadingBattles && battles.length > 0" class="battle-list">
          <div *ngFor="let battle of battles" class="battle-item">
            <div class="battle-info">
              <div class="battle-monsters">
                <div class="battle-monster" *ngIf="battle.monster1" [ngClass]="battle.monster1.elementType">
                  {{ battle.monster1.name }}
                </div>
                <span class="vs">VS</span>
                <div class="battle-monster" *ngIf="battle.monster2" [ngClass]="battle.monster2.elementType">
                  {{ battle.monster2.name }}
                </div>
              </div>
              
              <div class="battle-result">
                <span class="winner-label">Winner:</span>
                <span class="winner-name" *ngIf="battle.winner" [ngClass]="battle.winner.elementType">
                  {{ battle.winner.name }}
                </span>
              </div>
              
              <div class="battle-date">
                {{ battle.date | date:'medium' }}
              </div>
            </div>
            
            <div class="battle-actions">
              <a [routerLink]="['/battles', battle.id]" class="btn btn-small">View Replay</a>
            </div>
          </div>
        </div>
      </div>
      
      <div *ngIf="errorMessage" class="error-message text-center mt-3">
        {{ errorMessage }}
      </div>
    </div>
  `,
  styles: [`
    .battles-container {
      display: grid;
      grid-template-columns: 1fr;
      gap: 20px;
      padding: 20px 0;
    }
    
    @media (min-width: 768px) {
      .battles-container {
        grid-template-columns: 1fr 1fr;
      }
    }
    
    .w-100 {
      width: 100%;
    }
    
    .battle-list {
      max-height: 500px;
      overflow-y: auto;
    }
    
    .battle-item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 15px;
      margin-bottom: 15px;
      background-color: rgba(255, 255, 255, 0.05);
      border-radius: 4px;
    }
    
    .battle-monsters {
      display: flex;
      align-items: center;
      gap: 10px;
      margin-bottom: 10px;
    }
    
    .battle-monster {
      padding: 5px 10px;
      border-radius: 4px;
      font-weight: 500;
    }
    
    .battle-monster.fire {
      background-color: rgba(255, 87, 34, 0.2);
      color: var(--fire-color);
    }
    
    .battle-monster.water {
      background-color: rgba(33, 150, 243, 0.2);
      color: var(--water-color);
    }
    
    .battle-monster.wind {
      background-color: rgba(76, 175, 80, 0.2);
      color: var(--wind-color);
    }
    
    .vs {
      font-weight: 600;
      color: var(--secondary-color);
    }
    
    .battle-result {
      margin-bottom: 5px;
    }
    
    .winner-label {
      margin-right: 5px;
      font-size: 0.9rem;
    }
    
    .winner-name {
      font-weight: 600;
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
      font-size: 0.8rem;
      color: #aaa;
    }
    
    .btn-small {
      padding: 5px 10px;
      font-size: 0.8rem;
    }
  `]
})
export class BattlesComponent implements OnInit {
  battleForm: FormGroup;
  monsters: Monster[] = [];
  battles: BattleWithMonsters[] = [];
  isLoadingMonsters = true;
  isLoadingBattles = true;
  isStartingBattle = false;
  errorMessage = '';
  
  constructor(
    private fb: FormBuilder,
    private battleService: BattleService,
    private monsterService: MonsterService
  ) {
    this.battleForm = this.fb.group({
      monster1Id: ['', Validators.required],
      monster2Id: ['', Validators.required]
    }, { validators: this.differentMonstersValidator });
  }
  
  ngOnInit(): void {
    this.loadMonsters();
    this.loadBattles();
  }
  
  differentMonstersValidator(form: FormGroup) {
    const monster1Id = form.get('monster1Id')?.value;
    const monster2Id = form.get('monster2Id')?.value;
    
    if (monster1Id && monster2Id && monster1Id === monster2Id) {
      return { sameMonster: true };
    }
    
    return null;
  }
  
  loadMonsters(): void {
    this.isLoadingMonsters = true;
    
    this.monsterService.getMonsters().subscribe({
      next: (monsters) => {
        this.monsters = monsters;
        this.isLoadingMonsters = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load monsters. Please try again.';
        this.isLoadingMonsters = false;
        console.error('Error loading monsters:', error);
      }
    });
  }
  
  loadBattles(): void {
    this.isLoadingBattles = true;
    
    this.battleService.getBattles().subscribe({
      next: (battles) => {
        this.battles = battles;
        this.isLoadingBattles = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load battle history. Please try again.';
        this.isLoadingBattles = false;
        console.error('Error loading battles:', error);
      }
    });
  }
  
  startBattle(): void {
    if (this.battleForm.invalid) return;
    
    this.isStartingBattle = true;
    const { monster1Id, monster2Id } = this.battleForm.value;
    
    this.battleService.startBattle(monster1Id, monster2Id).subscribe({
      next: (battle) => {
        this.isStartingBattle = false;
        this.loadBattles();
        this.battleForm.reset();
      },
      error: (error) => {
        this.errorMessage = 'Failed to start battle. Please try again.';
        this.isStartingBattle = false;
        console.error('Error starting battle:', error);
      }
    });
  }
}
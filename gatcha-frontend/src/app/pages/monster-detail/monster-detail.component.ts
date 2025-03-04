import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MonsterService } from '../../services/monster.service';
import { PlayerService } from '../../services/player.service';
import { Monster, Skill } from '../../models/monster.model';

@Component({
  selector: 'app-monster-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="monster-detail-container">
      <div *ngIf="isLoading" class="text-center">
        <p>Loading monster details...</p>
      </div>
      
      <div *ngIf="!isLoading && monster" class="monster-detail">
        <div class="card monster-info">
          <div class="monster-header" [ngClass]="monster.elementType">
            <h2>{{ monster.name }}</h2>
            <span class="element-badge">{{ monster.elementType | titlecase }}</span>
          </div>
          
          <div class="monster-level-info">
            <div class="level-display">
              <span class="level-label">Level {{ monster.level }}</span>
            </div>
            
            <div class="progress-container">
              <div class="progress-bar">
                <div 
                  class="progress-fill" 
                  [style.width.%]="(monster.experience / monster.maxExperience) * 100"
                ></div>
              </div>
              <div class="progress-text">
                {{ monster.experience }} / {{ monster.maxExperience }} XP
              </div>
            </div>
          </div>
          
          <div class="monster-stats">
            <div class="stat-group">
              <div class="stat">
                <span class="stat-label">HP</span>
                <span class="stat-value">{{ monster.hp }}</span>
              </div>
              <div class="stat">
                <span class="stat-label">ATK</span>
                <span class="stat-value">{{ monster.atk }}</span>
              </div>
            </div>
            
            <div class="stat-group">
              <div class="stat">
                <span class="stat-label">DEF</span>
                <span class="stat-value">{{ monster.def }}</span>
              </div>
              <div class="stat">
                <span class="stat-label">VIT</span>
                <span class="stat-value">{{ monster.vit }}</span>
              </div>
            </div>
          </div>
          
          <div class="monster-actions">
            <button 
              class="btn" 
              (click)="gainExperience()" 
              [disabled]="isGainingExp"
            >
              {{ isGainingExp ? 'Training...' : 'Train (+XP)' }}
            </button>
            
            <button 
              class="btn btn-secondary" 
              (click)="levelUp()" 
              [disabled]="isLevelingUp || monster.experience < monster.maxExperience"
            >
              {{ isLevelingUp ? 'Leveling Up...' : 'Level Up' }}
            </button>
            
            <button 
              class="btn btn-danger" 
              (click)="releaseMonster()" 
              [disabled]="isReleasing"
            >
              {{ isReleasing ? 'Releasing...' : 'Release Monster' }}
            </button>
          </div>
        </div>
        
        <div class="card skills-card">
          <h3>Skills</h3>
          
          <div class="skills-list">
            <div *ngFor="let skill of monster.skills" class="skill-item">
              <div class="skill-header">
                <h4>{{ skill.name }}</h4>
                <span class="skill-level">Lv. {{ skill.level }}/{{ skill.maxLevel }}</span>
              </div>
              
              <div class="skill-details">
                <div class="skill-stat">
                  <span class="skill-label">Base Damage:</span>
                  <span class="skill-value">{{ skill.baseDamage }}</span>
                </div>
                
                <div class="skill-stat">
                  <span class="skill-label">Ratio:</span>
                  <span class="skill-value">{{ skill.statRatio }}x {{ skill.statType.toUpperCase() }}</span>
                </div>
                
                <div class="skill-stat">
                  <span class="skill-label">Cooldown:</span>
                  <span class="skill-value">{{ skill.cooldown }} turns</span>
                </div>
              </div>
              
              <button 
                class="btn skill-upgrade-btn" 
                (click)="upgradeSkill(skill)" 
                [disabled]="isUpgradingSkill || skill.level >= skill.maxLevel"
              >
                {{ isUpgradingSkill ? 'Upgrading...' : 'Upgrade Skill' }}
              </button>
            </div>
          </div>
        </div>
      </div>
      
      <div *ngIf="errorMessage" class="error-message text-center mt-3">
        {{ errorMessage }}
      </div>
      
      <div class="back-link">
        <a routerLink="/monsters" class="btn btn-secondary">Back to Monsters</a>
      </div>
    </div>
  `,
  styles: [`
    .monster-detail-container {
      padding: 20px 0;
    }
    
    .monster-detail {
      display: grid;
      grid-template-columns: 1fr;
      gap: 20px;
    }
    
    @media (min-width: 768px) {
      .monster-detail {
        grid-template-columns: 1fr 1fr;
      }
    }
    
    .monster-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding-bottom: 15px;
      margin-bottom: 20px;
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
      padding: 5px 10px;
      border-radius: 4px;
      font-size: 0.9rem;
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
    
    .monster-level-info {
      margin-bottom: 20px;
    }
    
    .level-display {
      margin-bottom: 10px;
    }
    
    .level-label {
      font-size: 1.2rem;
      font-weight: 600;
    }
    
    .progress-container {
      margin-bottom: 15px;
    }
    
    .progress-bar {
      height: 15px;
      background-color: #333;
      border-radius: 10px;
      overflow: hidden;
      margin-bottom: 5px;
    }
    
    .progress-fill {
      height: 100%;
      background-color: var(--primary-color);
      transition: width 0.3s ease;
    }
    
    .progress-text {
      font-size: 0.9rem;
      text-align: right;
    }
    
    .monster-stats {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 15px;
      margin-bottom: 20px;
    }
    
    .stat {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 10px;
      background-color: rgba(255, 255, 255, 0.05);
      border-radius: 4px;
    }
    
    .stat-label {
      font-size: 0.9rem;
      margin-bottom: 5px;
    }
    
    .stat-value {
      font-size: 1.2rem;
      font-weight: 600;
    }
    
    .monster-actions {
      display: flex;
      flex-direction: column;
      gap: 10px;
    }
    
    .skills-card h3 {
      margin-bottom: 15px;
    }
    
    .skills-list {
      display: flex;
      flex-direction: column;
      gap: 15px;
    }
    
    .skill-item {
      padding: 15px;
      background-color: rgba(255, 255, 255, 0.05);
      border-radius: 4px;
    }
    
    .skill-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 10px;
    }
    
    .skill-level {
      font-size: 0.9rem;
      color: var(--secondary-color);
    }
    
    .skill-details {
      margin-bottom: 15px;
    }
    
    .skill-stat {
      display: flex;
      justify-content: space-between;
      margin-bottom: 5px;
    }
    
    .skill-upgrade-btn {
      width: 100%;
    }
    
    .btn-danger {
      background-color: var(--error-color);
    }
    
    .btn-danger:hover {
      background-color: #b00020;
    }
    
    .back-link {
      margin-top: 20px;
      text-align: center;
    }
  `]
})
export class MonsterDetailComponent implements OnInit {
  monster: Monster | null = null;
  isLoading = true;
  isGainingExp = false;
  isLevelingUp = false;
  isUpgradingSkill = false;
  isReleasing = false;
  errorMessage = '';
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private monsterService: MonsterService,
    private playerService: PlayerService
  ) {}
  
  ngOnInit(): void {
    this.loadMonster();
  }
  
  loadMonster(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      this.errorMessage = 'Monster ID not found';
      this.isLoading = false;
      return;
    }
    
    this.monsterService.getMonster(id).subscribe({
      next: (monster) => {
        this.monster = monster;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load monster details. Please try again.';
        this.isLoading = false;
        console.error('Error loading monster:', error);
      }
    });
  }
  
  gainExperience(): void {
    if (!this.monster) return;
    
    this.isGainingExp = true;
    // Gain a random amount between 10 and 30
    const amount = Math.floor(Math.random() * 21) + 10;
    
    this.monsterService.gainExperience(this.monster.id, amount).subscribe({
      next: (monster) => {
        this.monster = monster;
        this.isGainingExp = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to train monster. Please try again.';
        this.isGainingExp = false;
        console.error('Error gaining experience:', error);
      }
    });
  }
  
  levelUp(): void {
    if (!this.monster || this.monster.experience < this.monster.maxExperience) return;
    
    this.isLevelingUp = true;
    
    this.monsterService.levelUp(this.monster.id).subscribe({
      next: (monster) => {
        this.monster = monster;
        this.isLevelingUp = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to level up monster. Please try again.';
        this.isLevelingUp = false;
        console.error('Error leveling up:', error);
      }
    });
  }
  
  upgradeSkill(skill: Skill): void {
    if (!this.monster || skill.level >= skill.maxLevel) return;
    
    this.isUpgradingSkill = true;
    
    this.monsterService.upgradeSkill(this.monster.id, skill.id).subscribe({
      next: (updatedSkill) => {
        if (this.monster) {
          // Update the skill in the monster's skills array
          const skillIndex = this.monster.skills.findIndex(s => s.id === updatedSkill.id);
          if (skillIndex !== -1) {
            this.monster.skills[skillIndex] = updatedSkill;
          }
        }
        this.isUpgradingSkill = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to upgrade skill. Please try again.';
        this.isUpgradingSkill = false;
        console.error('Error upgrading skill:', error);
      }
    });
  }
  
  releaseMonster(): void {
    if (!this.monster) return;
    
    if (!confirm(`Are you sure you want to release ${this.monster.name}? This action cannot be undone.`)) {
      return;
    }
    
    this.isReleasing = true;
    
    this.playerService.removeMonster(this.monster.id).subscribe({
      next: () => {
        this.isReleasing = false;
        this.router.navigate(['/monsters']);
      },
      error: (error) => {
        this.errorMessage = 'Failed to release monster. Please try again.';
        this.isReleasing = false;
        console.error('Error releasing monster:', error);
      }
    });
  }
}
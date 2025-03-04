import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink,ActivatedRoute } from '@angular/router';
import { PlayerService } from '../../services/player.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="profile-container">
      <div class="card profile-card">
        <h2 class="text-center">Player Profile</h2>
        
        <div *ngIf="isLoading" class="text-center">
          <p>Loading profile...</p>
        </div>
        
        <div *ngIf="!isLoading && user">
          <div class="profile-info">
            <div class="info-item">
              <span class="label">Username:</span>
              <span class="value">{{ user.username }}</span>
            </div>
            
            <div class="info-item">
              <span class="label">Level:</span>
              <span class="value">{{ user.level }}</span>
            </div>
            
            <div class="info-item">
              <span class="label">Experience:</span>
              <span class="value">{{ user.experience }} / {{ user.maxExperience }}</span>
            </div>
            
            <div class="info-item">
              <span class="label">Monsters:</span>
              <span class="value">{{ user.monsterIds.length }} / {{ user.maxMonsters }}</span>
            </div>
          </div>
          
          <div class="progress-container">
            <div class="progress-label">Level Progress</div>
            <div class="progress-bar">
              <div 
                class="progress-fill" 
                [style.width.%]="(user.experience / user.maxExperience) * 100"
              ></div>
            </div>
          </div>
          
          <div class="actions">
            <button 
              class="btn" 
              (click)="gainExperience()" 
              [disabled]="isGainingExp"
            >
              {{ isGainingExp ? 'Gaining...' : 'Gain Experience' }}
            </button>
            
            <button 
              class="btn btn-secondary" 
              (click)="levelUp()" 
              [disabled]="isLevelingUp || user.experience < user.maxExperience"
            >
              {{ isLevelingUp ? 'Leveling Up...' : 'Level Up' }}
            </button>
          </div>
          
          <div class="monster-section">
            <h3>Your Monsters</h3>
            
            <div *ngIf="user.monsterIds.length === 0" class="text-center">
              <p>You don't have any monsters yet.</p>
              <a routerLink="/summon" class="btn mt-3">Summon Your First Monster</a>
            </div>
            
            <div *ngIf="user.monsterIds.length > 0" class="text-center">
              <a routerLink="/monsters" class="btn">View All Monsters</a>
            </div>
          </div>
        </div>
        
        <div *ngIf="errorMessage" class="error-message text-center mt-3">
          {{ errorMessage }}
        </div>
      </div>
    </div>
  `,
  styles: [`
    .profile-container {
      display: flex;
      justify-content: center;
      padding: 20px 0;
    }
    
    .profile-card {
      width: 100%;
      max-width: 600px;
    }
    
    .profile-info {
      margin: 20px 0;
    }
    
    .info-item {
      display: flex;
      justify-content: space-between;
      margin-bottom: 10px;
      padding-bottom: 10px;
      border-bottom: 1px solid #333;
    }
    
    .label {
      font-weight: 600;
      color: var(--secondary-color);
    }
    
    .progress-container {
      margin: 20px 0;
    }
    
    .progress-label {
      margin-bottom: 5px;
      font-weight: 500;
    }
    
    .progress-bar {
      height: 20px;
      background-color: #333;
      border-radius: 10px;
      overflow: hidden;
    }
    
    .progress-fill {
      height: 100%;
      background-color: var(--primary-color);
      transition: width 0.3s ease;
    }
    
    .actions {
      display: flex;
      gap: 10px;
      margin: 20px 0;
    }
    
    .monster-section {
      margin-top: 30px;
    }
    
    .monster-section h3 {
      margin-bottom: 15px;
      text-align: center;
    }
  `]
})
export class ProfileComponent implements OnInit {
  user: User | null = null;
  isLoading = true;
  isGainingExp = false;
  isLevelingUp = false;
  errorMessage = '';
  
  constructor(private playerService: PlayerService,  private route: ActivatedRoute,) {}
  
  ngOnInit(): void {
     //  récupérer le username depuis l'URL
     this.route.queryParams.subscribe(params => {
      const username = params['username'];

      if (username) {
        this.loadProfile(username);
      } else {
        this.errorMessage = 'No username provided.';
      }
    });
  }
  
  loadProfile(username: string): void {
    this.isLoading = true;
    this.playerService.getProfile(username).subscribe({
      next: (user) => {
        this.user = user;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load profile. Please try again.';
        this.isLoading = false;
        console.error('Error loading profile:', error);
      }
    });
  }
  
  gainExperience(): void {
    if (!this.user) return;
    
    this.isGainingExp = true;
    // Gain a random amount between 10 and 30
    const amount = Math.floor(Math.random() * 21) + 10;
    
    this.playerService.gainExperience(amount).subscribe({
      next: (user) => {
        this.user = user;
        this.isGainingExp = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to gain experience. Please try again.';
        this.isGainingExp = false;
        console.error('Error gaining experience:', error);
      }
    });
  }
  
  levelUp(): void {
    if (!this.user || this.user.experience < this.user.maxExperience) return;
    
    this.isLevelingUp = true;
    
    this.playerService.levelUp().subscribe({
      next: (user) => {
        this.user = user;
        this.isLevelingUp = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to level up. Please try again.';
        this.isLevelingUp = false;
        console.error('Error leveling up:', error);
      }
    });
  }
}
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink,ActivatedRoute } from '@angular/router';
import { PlayerService } from '../../services/player.service';
import { User } from '../../models/user.model';
import { AuthService } from '../../services/auth.service';  

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: User | null = null;
  isLoading = true;
  isGainingExp = false;
  isLevelingUp = false;
  errorMessage = '';
  
  constructor(private playerService: PlayerService,  private route: ActivatedRoute, private authService : AuthService) {}
  
  ngOnInit(): void {
     //  récupérer le username depuis l'URL
     const username = this.authService.getUsername();
      
      if (username) {
        this.loadProfile(username);
      } else {
        this.errorMessage = 'No username provided.';
      }
  }

  loadProfile(username: string): void {
    this.isLoading = true;
    this.playerService.getProfile(username).subscribe({
      next: (user) => {
        this.user = user;
        console.log('Player récupéré:', this.user);
        // vérifier que monsterList est défini
        if (!this.user.monsterList) {
          this.user.monsterList = []; // Initialisez la liste à vide si elle est undefined
        }
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
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
  templateUrl: './summon.component.html',
  styleUrls: ['./summon.component.css']
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
    if (!this.user || this.user.monsterList.length >= this.user.maxMonsters) return;
    
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
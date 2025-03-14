import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, ActivatedRoute } from '@angular/router';
import { SummonService } from '../../services/summon.service';
import { PlayerService } from '../../services/player.service';
import { Monster } from '../../models/monster.model';
import { User } from '../../models/user.model';
import { AuthService } from '../../services/auth.service';

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
    private route: ActivatedRoute,
    private authService : AuthService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const username = this.authService.getUsername();

      if (username) {
        this.loadProfile(username);
      } else {
        this.errorMessage = 'Aucun nom d\'utilisateur fourni.';
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
        this.errorMessage = 'Échec du chargement du profil. Veuillez réessayer.';
        this.isLoadingProfile = false;
        console.error('Erreur de chargement du profil:', error);
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
        console.error('Erreur de chargement de l\'historique des invocations:', error);
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
        this.errorMessage = 'Échec de l\'invocation d\'un monstre. Veuillez réessayer.';
        this.isSummoning = false;
        console.error('Erreur lors de l\'invocation du monstre :', error);
      }
    });
  }
}

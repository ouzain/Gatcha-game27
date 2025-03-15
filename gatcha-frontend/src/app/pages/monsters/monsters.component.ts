import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MonsterService } from '../../services/monster.service';
import { PlayerService } from '../../services/player.service';
import { Monster } from '../../models/monster.model';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user.model';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-monsters',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './monsters.component.html',
  styleUrls: ['./monsters.component.css']
})
export class MonstersComponent implements OnInit {
  monsters: Monster[] = [];
  isLoading = true;
  errorMessage = '';
  user: User | null = null;

  constructor(
    private monsterService: MonsterService,
    private playerService: PlayerService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadMonsters();
  }

  loadMonsters(): void {
    this.isLoading = true;
    this.monsters = [];  // Réinitialiser la liste des monstres

    const username = this.authService.getUsername(); // Récupérer le username du joueur connecté

    if (username) {
      // Appel à votre service PlayerService pour obtenir les monstres du joueur
      this.playerService.getProfile(username).subscribe({
        next: (user) => {
          this.user = user;
          console.log('Joueur récupéré:', this.user);

          // Vérifier que monsterList est défini et non nul
          if (!this.user.monsterList || this.user.monsterList.length === 0) {
            this.isLoading = false;
            return;  // Si la liste est vide, ne pas appeler fetchMonstersByIds
          }

          // Charger les monstres en fonction des IDs récupérés
          const monsterIds = this.user.monsterList;
          this.fetchMonstersByIds(monsterIds);
        },
        error: (error) => {
          this.errorMessage = 'Échec du chargement des monstres. Veuillez réessayer.';
          this.isLoading = false;
          console.error('Erreur lors du chargement des monstres du joueur:', error);
        }
      });
    } else {
      this.errorMessage = 'Utilisateur non connecté';
      this.isLoading = false;
    }
  }

  fetchMonstersByIds(monsterIds: number[]): void {
    // Si monsterIds est vide, n'effectuez aucune requête
    if (monsterIds.length === 0) {
      this.isLoading = false;
      return;
    }

    // Récupérer les monstres en fonction des IDs
    const monsterRequests = monsterIds.map(id => this.monsterService.getMonster(id)); // Créer un tableau de requêtes

    // Utiliser forkJoin pour attendre que toutes les requêtes soient terminées
    forkJoin(monsterRequests).subscribe({
      next: (monsters) => {
        this.monsters = monsters; // Combiner les résultats des monstres
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Erreur lors de la récupération des monstres. Veuillez réessayer.';
        this.isLoading = false;
        console.error('Erreur lors du chargement des monstres:', error);
      }
    });
  }
}

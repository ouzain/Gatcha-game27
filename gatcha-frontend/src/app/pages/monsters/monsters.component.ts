import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MonsterService } from '../../services/monster.service';
import { Monster } from '../../models/monster.model';

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
        this.errorMessage = 'Échec du chargement des monstres. Veuillez réessayer.';
        this.isLoading = false;
        console.error('Erreur lors du chargement des monstres:', error);
      }
    });
  }
}
// Compare this snippet from src/app/pages/monsters/monsters.component.html:
// <div *ngIf="isLoading">
//   <p>Loading monsters...</p>
// </div>
//
// <div *ngIf="errorMessage">
//   <p>{{ errorMessage }}</p>
// </div>
//
// <div *ngIf="!isLoading && !errorMessage">
//   <h1>Monsters</h1>
//   <ul>
//     <li *ngFor="let monster of monsters">
//       <a [routerLink]="['/monsters', monster.id]">{{ monster.name }}</a>
//     </li>
//   </ul>
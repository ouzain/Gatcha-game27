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
  templateUrl: './battles.component.html',
  styleUrls: ['./battles.component.css']
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
        this.errorMessage = 'Échec du lancement du combat. Veuillez réessayer';
        this.isStartingBattle = false;
        console.error('Erreur lors du lancement de la bataille : ', error);
      }
    });
  }
}
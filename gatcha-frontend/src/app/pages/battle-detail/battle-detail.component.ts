import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink,RouterModule } from '@angular/router';

import { BattleService } from '../../services/battle.service';
import { BattleWithMonsters, BattleAction } from '../../models/battle.model';

@Component({
  selector: 'app-battle-detail',
  standalone: true,
  imports: [CommonModule, RouterLink,RouterModule],
  templateUrl: './battle-detail.component.html',
  styleUrls: ['./battle-detail.component.css']
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
    // if (!this.battle) return '';
    
    // if (this.battle.monster1?.id === monsterId) {
    //   return this.battle.monster1.name;
    // } else if (this.battle.monster2?.id === monsterId) {
    //   return this.battle.monster2.name;
    // }
    
    return '';
  }
  
  getMonsterElement(monsterId: string): string {
    // if (!this.battle) return '';
    
    // if (this.battle.monster1?.id === monsterId) {
    //   return this.battle.monster1.elementType;
    // } else if (this.battle.monster2?.id === monsterId) {
    //   return this.battle.monster2.elementType;
    // }
    
    return '';
  }
  
  getSkillName(monsterId: string, skillId: string): string {
    // if (!this.battle) return '';
    
    // let monster = null;
    // if (this.battle.monster1?.id === monsterId) {
    //   monster = this.battle.monster1;
    // } else if (this.battle.monster2?.id === monsterId) {
    //   monster = this.battle.monster2;
    // }
    
    // if (monster) {
    //   const skill = monster.skills.find(s => s.id === skillId);
    //   return skill ? skill.name : '';
    // }
    
    return '';
  }
}
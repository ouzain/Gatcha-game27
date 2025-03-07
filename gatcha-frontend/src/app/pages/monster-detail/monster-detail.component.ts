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
  templateUrl: './monster-detail.component.html',
  styleUrls: ['./monster-detail.component.css']
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
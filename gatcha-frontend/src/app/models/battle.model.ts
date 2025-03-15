import { Monster } from './monster.model';

export interface BattleAction {
  turn: number;
  monsterId: string;
  skillId: string;
  damage: number;
  targetHp: number;
}

export interface Battle {
  id: string;
  monster1Id: string;
  monster2Id: string;
  winnerId: string;
  date: Date;
  actions: BattleAction[];
}

export interface BattleWithMonsters extends Battle {
  monster1?: Monster;
  monster2?: Monster;
  winner?: Monster;
}


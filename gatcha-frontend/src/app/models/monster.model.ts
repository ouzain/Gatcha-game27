export type ElementType = 'fire' | 'water' | 'wind';

export interface Skill {
  id: string;
  name: string;
  baseDamage: number;
  statRatio: number;
  statType: 'hp' | 'atk' | 'def' | 'vit';
  cooldown: number;
  level: number;
  maxLevel: number;
}

export interface Monster {
  id: string;
  name: string;
  elementType: ElementType;
  hp: number;
  atk: number;
  def: number;
  vit: number;
  level: number;
  experience: number;
  maxExperience: number;
  skills: Skill[];
  ownerId: string;
}

export interface MonsterTemplate {
  id: string;
  name: string;
  elementType: ElementType;
  baseHp: number;
  baseAtk: number;
  baseDef: number;
  baseVit: number;
  summonRate: number;
  skills: Omit<Skill, 'level'>[];
}
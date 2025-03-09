export type ElementType = 'fire' | 'water' | 'wind';

export interface Ratio{
  stat: ElementType;
  percent: number; // Correspond à "percent" dans l'exemple JSON
  
}

export interface Skill {
  num: number; // Correspond à "num" dans l'exemple JSON
  dmg: number; 
  lvlMax: number; 
  ratio: Ratio;
  cooldown: number; 
  level: number; // Utilisé pour représenter le niveau actuel de la compétence
  
}


export interface Monster {
  id: number; // Correspond à l'id du monstre
  name: string; 
  elementType: ElementType; // Correspond à "element"
  hp: number; 
  atk: number; 
  def: number; 
  vit: number; 
  level: number; // pour permettre la gestion du niveau du monstre
  experience: number; 
  maxExperience: number; // pour permettre la gestion de l'expérience maximale
  skills: Skill[]; 
  lootRate: number; 
  ownerUsername: string; // si le monstre appartient à un joueur
}

export interface MonsterTemplate {
  id: number; // Correspond à "_id"
  elementType: ElementType;
  baseHp: number; // Correspond à "hp" de base
  baseAtk: number; // Correspond à "atk" de base
  baseDef: number; // Correspond à "def" de base
  baseVit: number; // Correspond à "vit" de base
  summonRate: number; // Correspond à "lootRate"
  skills: Omit<Skill, 'level'>[]; // Exclut "level" car il ne fait pas partie du modèle template
}

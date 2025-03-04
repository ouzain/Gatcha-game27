export interface User {
  id?: string;
  username: string;
  level: number;
  experience: number;
  maxExperience: number;
  monsterIds: string[];
  maxMonsters: number;
}
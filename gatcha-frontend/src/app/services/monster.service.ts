import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Monster, Skill } from '../models/monster.model';

@Injectable({
  providedIn: 'root'
})
export class MonsterService {
  constructor(private http: HttpClient) {}

  getMonsters(): Observable<Monster[]> {
    return this.http.get<Monster[]>(`${environment.monsterApiUrl}/monsters`);
  }

  getMonster(id: string): Observable<Monster> {
    return this.http.get<Monster>(`${environment.monsterApiUrl}/monsters/${id}`);
  }

  gainExperience(id: string, amount: number): Observable<Monster> {
    return this.http.post<Monster>(`${environment.monsterApiUrl}/monsters/${id}/experience`, { amount });
  }

  levelUp(id: string): Observable<Monster> {
    return this.http.post<Monster>(`${environment.monsterApiUrl}/monsters/${id}/level-up`, {});
  }

  upgradeSkill(monsterId: string, skillId: string): Observable<Skill> {
    return this.http.post<Skill>(`${environment.monsterApiUrl}/monsters/${monsterId}/skills/${skillId}/upgrade`, {});
  }
}
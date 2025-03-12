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
    return this.http.get<Monster[]>(`${environment.monsterApiUrl}/get-all`);
  }

  getMonster(id: number): Observable<Monster> {
    return this.http.get<Monster>(`${environment.monsterApiUrl}/${id}`);
  }

  gainExperience(id: number, amount: number): Observable<Monster> {
    return this.http.post<Monster>(`${environment.monsterApiUrl}/${id}/experience`, { amount });
  }

  levelUp(id: number): Observable<Monster> {
    return this.http.post<Monster>(`${environment.monsterApiUrl}/${id}/level-up`, {});
  }

  upgradeSkill(monsterId: number, skillId: number): Observable<Skill> {
    return this.http.post<Skill>(`${environment.monsterApiUrl}/${monsterId}/skills/${skillId}/upgrade`, {});
  }
}
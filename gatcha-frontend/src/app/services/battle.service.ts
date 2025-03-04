import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Battle, BattleWithMonsters } from '../models/battle.model';

@Injectable({
  providedIn: 'root'
})
export class BattleService {
  constructor(private http: HttpClient) {}

  startBattle(monster1Id: string, monster2Id: string): Observable<Battle> {
    return this.http.post<Battle>(`${environment.combatApiUrl}/battles`, { monster1Id, monster2Id });
  }

  getBattles(): Observable<BattleWithMonsters[]> {
    return this.http.get<BattleWithMonsters[]>(`${environment.combatApiUrl}/battles`);
  }

  getBattle(id: string): Observable<BattleWithMonsters> {
    return this.http.get<BattleWithMonsters>(`${environment.combatApiUrl}/battles/${id}`);
  }

  replayBattle(id: string): Observable<BattleWithMonsters> {
    return this.http.get<BattleWithMonsters>(`${environment.combatApiUrl}/battles/${id}/replay`);
  }
}
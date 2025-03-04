import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  constructor(private http: HttpClient) {}

  getProfile(username: string): Observable<User> {
    return this.http.get<User>(`${environment.playerApiUrl}/get-user`, {
      params: { username }
    });
  }

  getMonsterList(): Observable<string[]> {
    return this.http.get<string[]>(`${environment.playerApiUrl}/player/monsters`);
  }

  getLevel(): Observable<number> {
    return this.http.get<number>(`${environment.playerApiUrl}/player/level`);
  }

  gainExperience(amount: number): Observable<User> {
    return this.http.post<User>(`${environment.playerApiUrl}/player/experience`, { amount });
  }

  levelUp(): Observable<User> {
    return this.http.post<User>(`${environment.playerApiUrl}/player/level-up`, {});
  }

  addMonster(monsterId: string): Observable<User> {
    return this.http.post<User>(`${environment.playerApiUrl}/player/monsters`, { monsterId });
  }

  removeMonster(monsterId: string): Observable<User> {
    return this.http.delete<User>(`${environment.playerApiUrl}/player/monsters/${monsterId}`);
  }
}
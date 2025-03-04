import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Monster } from '../models/monster.model';

@Injectable({
  providedIn: 'root'
})
export class SummonService {
  constructor(private http: HttpClient) {}

  summonMonster(): Observable<Monster> {
    return this.http.post<Monster>(`${environment.invocationApiUrl}/summon`, {});
  }

  getSummonHistory(): Observable<Monster[]> {
    return this.http.get<Monster[]>(`${environment.invocationApiUrl}/summon/history`);
  }

  regenerateSummons(): Observable<{ success: boolean }> {
    return this.http.post<{ success: boolean }>(`${environment.invocationApiUrl}/summon/regenerate`, {});
  }
}
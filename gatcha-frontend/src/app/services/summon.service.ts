import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable, switchMap } from 'rxjs';
import { environment } from '../../environments/environment';
import { Monster } from '../models/monster.model';
import { MonsterService } from './monster.service';
import { AuthService } from './auth.service';  

@Injectable({
  providedIn: 'root'
})
export class SummonService {
  summondedMonsterId: number = 0;
  constructor(
    private http: HttpClient,
    private monsterService: MonsterService,
    private authService: AuthService  // Injection du AuthService
  ) {}

  summonMonster(): Observable<Monster> {
    const token = this.authService.getToken(); // Récupération du token depuis AuthService

    // Vérification si le token existe avant de faire la requête
    if (!token) {
      throw new Error('Token non disponible');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    // On passe le token dans la requête HTTP
    const params = new HttpParams().set('Authorization', token);

    return this.http.post<any>(`${environment.playerApiUrl}/acquire-monster`, null, { params }).pipe(
      switchMap(response => {
      // Récupérer l'ID du monstre depuis la réponse
      const monsterId = response.data;
      this.summondedMonsterId = monsterId;
      console.log('Monstre invoqué avec l\'id :', monsterId);

      // Utiliser l'ID pour récupérer le monstre complet via MonsterService
      return this.monsterService.getMonster(monsterId);
      })
    );
  }

  getSummonHistory(): Observable<Monster[]> {
    return this.http.get<Monster[]>(`${environment.invocationApiUrl}/summon/history`);
  }

  regenerateSummons(): Observable<{ success: boolean }> {
    return this.http.post<{ success: boolean }>(`${environment.invocationApiUrl}/summon/regenerate`, {});
  }
}

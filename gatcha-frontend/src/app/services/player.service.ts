import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders  } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { User } from '../models/user.model';
import { map } from 'rxjs/operators';
import { AuthService } from './auth.service';

interface ApiResponse {
  success: boolean;
  data: any;
  message?: string;
}

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  constructor(private http: HttpClient, private authService : AuthService ) {}

  getProfile(username: string): Observable<User> {
    return this.http.get<ApiResponse>(`${environment.playerApiUrl}/get-user`, {
      params: { username }
    }).pipe(
      map(response => {
        if (response.success) {
          return response.data as User;  // Ici nous supposons que 'data' contient les informations du joueur
        } else {
          throw new Error(response.message || 'Failed to load player');
        }
      })
    );
  }

  getMonsterList(): Observable<string[]> {
    return this.http.get<string[]>(`${environment.playerApiUrl}/player/monsters`);
  }

  getLevel(): Observable<number> {
    return this.http.get<number>(`${environment.playerApiUrl}/player/level`);
  }

  gainExperience(amount: number): Observable<User> {
    const token = this.authService.getToken(); // Récupérer le token d'authentification depuis le localStorage
    if (!token) {
      throw new Error("Token d'authentification manquant");
    }

    // Définir les en-têtes avec le token d'authentification
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    // Envoi de la requête avec le paramètre exp dans l'URL
    return this.http.post<User>(`${environment.playerApiUrl}/player/add-exp-user?exp=${amount}`, {}, { headers });
  }

  levelUp(): Observable<User> {
    return this.http.post<User>(`${environment.playerApiUrl}/player/level-up`, {});
  }

  addMonster(monsterId: number): Observable<User> {
    return this.http.post<User>(`${environment.playerApiUrl}/player/monsters`, { monsterId });
  }

  removeMonster(monsterId: number): Observable<User> {
    return this.http.delete<User>(`${environment.playerApiUrl}/player/monsters/${monsterId}`);
  }
}
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';

interface AuthResponse {
  message: string;
  success: boolean;
  data: string; // Le token est dans le champ "data"
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly TOKEN_KEY = 'auth_token';
  private readonly USERNAME_KEY = 'username';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(this.hasToken());
  
  isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(private http: HttpClient) {
    this.checkTokenValidity();
  }

  login(username: string, password: string): Observable<boolean> {
    // Nettoyer le localStorage avant de tenter une nouvelle connexion
    this.clearLocalStorage();

    return this.http.post<AuthResponse>(`${environment.playerApiUrl}/authenticate`, { username, password })
      .pipe(
        tap(response => {
          const token = response.data; // Récupérer le token du champ "data"
          if (token) {
            this.setToken(token); // Stocker le token
            this.setUsername(username); // Stocker le username
            this.isAuthenticatedSubject.next(true); // Marquer l'utilisateur comme authentifié
          } else {
            throw new Error('Token non trouvé dans la réponse');
          }
        }),
        map(() => true), // Retourne true si tout se passe bien
        catchError(error => {
          console.error('Login error:', error);
          return throwError(() => new Error(error.error?.message || 'Login failed')); // Gérer l'erreur si le login échoue
        })
      );
  }

  register(username: string, password: string): Observable<boolean> {
    return this.http.post<AuthResponse>(`${environment.playerApiUrl}/add`, { username, password })
      .pipe(
        tap(response => {
          const token = response.data; // Récupérer le token de la réponse
          this.setToken(token); // Stocker le token
          this.setUsername(username); // Stocker le username
          this.isAuthenticatedSubject.next(true); // Marquer l'utilisateur comme authentifié
        }),
        map(() => true), // Retourne true si tout se passe bien
        catchError(error => {
          console.error('Registration error:', error);
          return throwError(() => new Error(error.error?.message || 'Registration failed')); // Gérer l'erreur si l'enregistrement échoue
        })
      );
  }

  logout(): void {
    this.clearLocalStorage();
    this.isAuthenticatedSubject.next(false);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY); // Retourner le token stocké dans le localStorage
  }

  getUsername(): string | null {
    return localStorage.getItem(this.USERNAME_KEY); // Retourner le username stocké dans le localStorage
  }

  private setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token); // Stocker le token dans le localStorage
  }

  private setUsername(username: string): void {
    localStorage.setItem(this.USERNAME_KEY, username); // Stocker le username dans le localStorage
  }

  private hasToken(): boolean {
    return !!this.getToken(); // Vérifier si le token existe dans le localStorage
  }

  private clearLocalStorage(): void {
    // Supprimer tous les éléments stockés dans le localStorage
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USERNAME_KEY);
  }

  private checkTokenValidity(): void {
    if (this.hasToken()) {
      this.http.get<{ valid: boolean }>(`${environment.authApiUrl}/validate`) // Vérifier la validité du token
        .pipe(
          catchError(() => {
            this.logout();
            return of({ valid: false });
          })
        )
        .subscribe(response => {
          if (!response.valid) {
            this.logout(); // Si le token n'est pas valide, se déconnecter
          }
        });
    }
  }
}

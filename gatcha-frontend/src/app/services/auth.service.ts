import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';

interface AuthResponse {
  token: string;
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
    return this.http.post(`${environment.authApiUrl}/login`, { username, password }, { responseType: 'text' })
      .pipe(
        tap(token => {
          this.setToken(token);  // Stocke directement le token
          this.setUsername(username);
          this.isAuthenticatedSubject.next(true);
        }),
        map(() => true),
        catchError(error => {
          console.error('Login error:', error);
          return throwError(() => new Error(error.error?.message || 'Login failed'));
        })
      );
  }
  

  register(username: string, password: string): Observable<boolean> {
    return this.http.post<AuthResponse>(`${environment.playerApiUrl}/add`, { username, password })
      .pipe(
        tap(response => {
          this.setToken(response.token);
          this.setUsername(username);
          this.isAuthenticatedSubject.next(true);
        }),
        map(() => true),
        catchError(error => {
          console.error('Registration error:', error);
          return throwError(() => new Error(error.error?.message || 'Registration failed'));
        })
      );
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USERNAME_KEY);
    this.isAuthenticatedSubject.next(false);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getUsername(): string | null {
    return localStorage.getItem(this.USERNAME_KEY);
  }

  private setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  private setUsername(username: string): void {
    localStorage.setItem(this.USERNAME_KEY, username);
  }

  private hasToken(): boolean {
    return !!this.getToken();
  }

  private checkTokenValidity(): void {
    if (this.hasToken()) {
      this.http.get<{ valid: boolean }>(`${environment.authApiUrl}/auth/validate`)
        .pipe(
          catchError(() => {
            this.logout();
            return of({ valid: false });
          })
        )
        .subscribe(response => {
          if (!response.valid) {
            this.logout();
          }
        });
    }
  }
}
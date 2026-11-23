import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, tap } from 'rxjs';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
  tokenType: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/v1/auth';

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, request).pipe(
      tap(response => localStorage.setItem('giro_access_token', response.accessToken))
    );
  }

  logout(): void {
    localStorage.removeItem('giro_access_token');
  }

  isAuthenticated(): boolean {
    return Boolean(localStorage.getItem('giro_access_token'));
  }

  getToken(): string | null {
    return localStorage.getItem('giro_access_token');
  }
}

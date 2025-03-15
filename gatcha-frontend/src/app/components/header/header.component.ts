import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './header.Component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  isAuthenticated$ = this.authService.isAuthenticated$;
  
  constructor(private authService: AuthService , private router: Router) {}
  
  logout(event: Event): void {
    event.preventDefault();
    this.authService.logout();
      // Redirection vers la page d'accueil
      this.router.navigate(['']);
  }
}
import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { 
    path: '', 
    loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent) 
  },
  { 
    path: 'login', 
    loadComponent: () => import('./pages/login/login.component').then(m => m.LoginComponent) 
  },
  { 
    path: 'register', 
    loadComponent: () => import('./pages/register/register.component').then(m => m.RegisterComponent) 
  },
  { 
    path: 'profile', 
    loadComponent: () => import('./pages/profile/profile.component').then(m => m.ProfileComponent),
    canActivate: [authGuard]
  },
  { 
    path: 'monsters', 
    loadComponent: () => import('./pages/monsters/monsters.component').then(m => m.MonstersComponent),
    canActivate: [authGuard]
  },
  { 
    path: 'monsters/:id', 
    loadComponent: () => import('./pages/monster-detail/monster-detail.component').then(m => m.MonsterDetailComponent),
    canActivate: [authGuard]
  },
  { 
    path: 'summon', 
    loadComponent: () => import('./pages/summon/summon.component').then(m => m.SummonComponent),
    canActivate: [authGuard]
  },
  { 
    path: 'battles', 
    loadComponent: () => import('./pages/battles/battles.component').then(m => m.BattlesComponent),
    canActivate: [authGuard]
  },
  { 
    path: 'battles/:id', 
    loadComponent: () => import('./pages/battle-detail/battle-detail.component').then(m => m.BattleDetailComponent),
    canActivate: [authGuard]
  },
  { 
    path: '**', 
    loadComponent: () => import('./pages/not-found/not-found.component').then(m => m.NotFoundComponent) 
  }
];
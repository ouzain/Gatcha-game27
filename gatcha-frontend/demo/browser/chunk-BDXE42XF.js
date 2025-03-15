import{a as I}from"./chunk-SNAX2NMN.js";import{a as S}from"./chunk-PTW3EDK3.js";import"./chunk-AUALO7ST.js";import{A as s,B as m,E as h,I as x,K as l,L as _,N as i,O as t,P as E,Q as b,R as v,S as p,T as n,U as f,V as c,ga as P,ka as C,ra as y,ua as M,v as u,w as g}from"./chunk-SP6T3Q2V.js";function L(r,d){r&1&&(i(0,"div",2)(1,"p"),n(2,"Chargement du profil..."),t()())}function w(r,d){r&1&&(i(0,"div",2)(1,"p"),n(2,"Vous n'avez encore aucun monstre."),t(),i(3,"a",18),n(4,"Invoquer votre premier monstre"),t()())}function O(r,d){r&1&&(i(0,"div",2)(1,"a",19),n(2,"Voir tous les monstres"),t()())}function k(r,d){if(r&1){let e=b();i(0,"div")(1,"div",6)(2,"div",7)(3,"span",8),n(4,"Nom d'utilisateur :"),t(),i(5,"span",9),n(6),t()(),i(7,"div",7)(8,"span",8),n(9,"Niveau :"),t(),i(10,"span",9),n(11),t()(),i(12,"div",7)(13,"span",8),n(14,"Exp\xE9rience :"),t(),i(15,"span",9),n(16),t()(),i(17,"div",7)(18,"span",8),n(19,"Monstres :"),t(),i(20,"span",9),n(21),t()()(),i(22,"div",10)(23,"div",11),n(24,"Progression du niveau"),t(),i(25,"div",12),E(26,"div",13),t()(),i(27,"div",14)(28,"button",15),v("click",function(){u(e);let a=p();return g(a.gainExperience())}),n(29),t(),i(30,"button",16),v("click",function(){u(e);let a=p();return g(a.levelUp())}),n(31),t()(),i(32,"div",17)(33,"h3"),n(34,"Vos monstres"),t(),x(35,w,5,0,"div",3)(36,O,3,0,"div",3),t()()}if(r&2){let e=p();s(6),f(e.user.username),s(5),f(e.user.level),s(5),c("",e.user.experience," "),s(5),c("",e.user.monsterList.length||0," "),s(5),_("width",e.user.experience/e.user.maxExperience*100,"%"),s(2),l("disabled",e.isGainingExp),s(),c(" ",e.isGainingExp?"En train d'acqu\xE9rir de l'exp\xE9rience...":"Gagner de l'exp\xE9rience"," "),s(),l("disabled",e.isLevelingUp||e.user.experience<e.user.maxExperience),s(),c(" ",e.isLevelingUp?"En train de monter en niveau...":"Monter de niveau"," "),s(4),l("ngIf",e.user.monsterList.length===0),s(),l("ngIf",e.user.monsterList.length>0)}}function U(r,d){if(r&1&&(i(0,"div",20),n(1),t()),r&2){let e=p();s(),c(" ",e.errorMessage," ")}}var z=(()=>{class r{constructor(e,o,a){this.playerService=e,this.route=o,this.authService=a,this.user=null,this.isLoading=!0,this.isGainingExp=!1,this.isLevelingUp=!1,this.errorMessage=""}ngOnInit(){let e=this.authService.getUsername();e?this.loadProfile(e):this.errorMessage="No username provided."}loadProfile(e){this.isLoading=!0,this.playerService.getProfile(e).subscribe({next:o=>{this.user=o,console.log("Player r\xE9cup\xE9r\xE9:",this.user),this.user.monsterList||(this.user.monsterList=[]),this.isLoading=!1},error:o=>{this.errorMessage="Failed to load profile. Please try again.",this.isLoading=!1,console.error("Error loading profile:",o)}})}gainExperience(){if(!this.user)return;this.isGainingExp=!0;let e=Math.floor(Math.random()*21)+10;this.playerService.gainExperience(e).subscribe({next:o=>{this.user=o,this.isGainingExp=!1},error:o=>{this.errorMessage="\xC9chec de l'acquisition de l'exp\xE9rience. Veuillez r\xE9essayer.",this.isGainingExp=!1,console.error("Error gaining experience:",o)}})}levelUp(){!this.user||this.user.experience<this.user.maxExperience||(this.isLevelingUp=!0,this.playerService.levelUp().subscribe({next:e=>{this.user=e,this.isLevelingUp=!1},error:e=>{this.errorMessage="Failed to level up. Please try again.",this.isLevelingUp=!1,console.error("Error leveling up:",e)}}))}static{this.\u0275fac=function(o){return new(o||r)(m(I),m(y),m(S))}}static{this.\u0275cmp=h({type:r,selectors:[["app-profile"]],decls:7,vars:3,consts:[[1,"profile-container"],[1,"card","profile-card"],[1,"text-center"],["class","text-center",4,"ngIf"],[4,"ngIf"],["class","error-message text-center mt-3",4,"ngIf"],[1,"profile-info"],[1,"info-item"],[1,"label"],[1,"value"],[1,"progress-container"],[1,"progress-label"],[1,"progress-bar"],[1,"progress-fill"],[1,"actions"],[1,"btn",3,"click","disabled"],[1,"btn","btn-secondary",3,"click","disabled"],[1,"monster-section"],["routerLink","/summon",1,"btn","mt-3"],["routerLink","/monsters",1,"btn"],[1,"error-message","text-center","mt-3"]],template:function(o,a){o&1&&(i(0,"div",0)(1,"div",1)(2,"h2",2),n(3,"Profil du joueur"),t(),x(4,L,3,0,"div",3)(5,k,37,12,"div",4)(6,U,2,1,"div",5),t()()),o&2&&(s(4),l("ngIf",a.isLoading),s(),l("ngIf",!a.isLoading&&a.user),s(),l("ngIf",a.errorMessage))},dependencies:[C,P,M],styles:[".profile-container[_ngcontent-%COMP%]{display:flex;justify-content:center;padding:20px 0}.profile-card[_ngcontent-%COMP%]{width:100%;max-width:600px}.profile-info[_ngcontent-%COMP%]{margin:20px 0}.info-item[_ngcontent-%COMP%]{display:flex;justify-content:space-between;margin-bottom:10px;padding-bottom:10px;border-bottom:1px solid #333}.label[_ngcontent-%COMP%]{font-weight:600;color:var(--secondary-color)}.progress-container[_ngcontent-%COMP%]{margin:20px 0}.progress-label[_ngcontent-%COMP%]{margin-bottom:5px;font-weight:500}.progress-bar[_ngcontent-%COMP%]{height:20px;background-color:#333;border-radius:10px;overflow:hidden}.progress-fill[_ngcontent-%COMP%]{height:100%;background-color:var(--primary-color);transition:width .3s ease}.actions[_ngcontent-%COMP%]{display:flex;gap:10px;margin:20px 0}.monster-section[_ngcontent-%COMP%]{margin-top:30px}.monster-section[_ngcontent-%COMP%]   h3[_ngcontent-%COMP%]{margin-bottom:15px;text-align:center}"]})}}return r})();export{z as ProfileComponent};

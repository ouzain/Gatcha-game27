import{a as I}from"./chunk-OS54V6FA.js";import{a as P}from"./chunk-5NYNIZ4Q.js";import{a as O}from"./chunk-PQSCT4QP.js";import"./chunk-AUALO7ST.js";import{$ as M,A as s,B as c,E as f,I as p,K as m,N as n,O as t,S as g,T as o,U as l,V as v,Z as x,_ as h,ea as _,fa as C,ga as S,i as u,ia as b,ka as y,ua as E}from"./chunk-SP6T3Q2V.js";var L=r=>["/monsters",r];function w(r,d){r&1&&(n(0,"div",1)(1,"p"),o(2,"Chargement des monstres..."),t()())}function T(r,d){r&1&&(n(0,"div",1)(1,"p"),o(2,"Vous n'avez encore aucun monstre."),t(),n(3,"a",5),o(4,"Invoquez votre premier monstre"),t()())}function k(r,d){if(r&1&&(n(0,"div",8)(1,"div",9)(2,"h3"),o(3),t(),n(4,"span",10),o(5),h(6,"titlecase"),t()(),n(7,"div",11)(8,"div",12)(9,"span",13),o(10,"Niveau :"),t(),n(11,"span",14),o(12),t()(),n(13,"div",12)(14,"span",13),o(15,"PV :"),t(),n(16,"span",14),o(17),t()(),n(18,"div",12)(19,"span",13),o(20,"ATK :"),t(),n(21,"span",14),o(22),t()(),n(23,"div",12)(24,"span",13),o(25,"DEF :"),t(),n(26,"span",14),o(27),t()(),n(28,"div",12)(29,"span",13),o(30,"VIT :"),t(),n(31,"span",14),o(32),t()()(),n(33,"div",15)(34,"a",16),o(35,"Voir les d\xE9tails"),t()()()),r&2){let e=d.$implicit;s(),m("ngClass",e.elementType),s(2),l(e.name),s(2),l(M(6,9,e.elementType)),s(7),l(e.level),s(5),l(e.hp),s(5),l(e.atk),s(5),l(e.def),s(5),l(e.vit),s(2),m("routerLink",x(11,L,e.id))}}function F(r,d){if(r&1&&(n(0,"div",6),p(1,k,36,13,"div",7),t()),r&2){let e=g();s(),m("ngForOf",e.monsters)}}function V(r,d){if(r&1&&(n(0,"div",17),o(1),t()),r&2){let e=g();s(),v(" ",e.errorMessage," ")}}var R=(()=>{class r{constructor(e,a,i){this.monsterService=e,this.playerService=a,this.authService=i,this.monsters=[],this.isLoading=!0,this.errorMessage="",this.user=null}ngOnInit(){this.loadMonsters()}loadMonsters(){this.isLoading=!0;let e=this.authService.getUsername();e?this.playerService.getProfile(e).subscribe({next:a=>{this.user=a,console.log("Joueur r\xE9cup\xE9r\xE9:",this.user),this.user.monsterList||(this.user.monsterList=[]);let i=this.user.monsterList;this.fetchMonstersByIds(i)},error:a=>{this.errorMessage="\xC9chec du chargement des monstres. Veuillez r\xE9essayer.",this.isLoading=!1,console.error("Erreur lors du chargement des monstres du joueur:",a)}}):(this.errorMessage="Utilisateur non connect\xE9",this.isLoading=!1)}fetchMonstersByIds(e){let a=e.map(i=>this.monsterService.getMonster(i));u(a).subscribe({next:i=>{this.monsters=i,this.isLoading=!1},error:i=>{this.errorMessage="Erreur lors de la r\xE9cup\xE9ration des monstres. Veuillez r\xE9essayer.",this.isLoading=!1,console.error("Erreur lors du chargement des monstres:",i)}})}static{this.\u0275fac=function(a){return new(a||r)(c(P),c(O),c(I))}}static{this.\u0275cmp=f({type:r,selectors:[["app-monsters"]],decls:7,vars:4,consts:[[1,"monsters-container"],[1,"text-center"],["class","text-center",4,"ngIf"],["class","monster-grid",4,"ngIf"],["class","error-message text-center mt-3",4,"ngIf"],["routerLink","/summon",1,"btn","mt-3"],[1,"monster-grid"],["class","card monster-card",4,"ngFor","ngForOf"],[1,"card","monster-card"],[1,"monster-header",3,"ngClass"],[1,"element-badge"],[1,"monster-stats"],[1,"stat"],[1,"stat-label"],[1,"stat-value"],[1,"monster-actions"],[1,"btn",3,"routerLink"],[1,"error-message","text-center","mt-3"]],template:function(a,i){a&1&&(n(0,"div",0)(1,"h2",1),o(2,"Vos Monstres"),t(),p(3,w,3,0,"div",2)(4,T,5,0,"div",2)(5,F,2,1,"div",3)(6,V,2,1,"div",4),t()),a&2&&(s(3),m("ngIf",i.isLoading&&!(!(i.user==null||i.user.monsterList==null)&&i.user.monsterList.length)),s(),m("ngIf",!i.isLoading&&i.monsters.length===0),s(),m("ngIf",!i.isLoading&&i.monsters.length>0),s(),m("ngIf",i.errorMessage))},dependencies:[y,_,C,S,b,E],styles:[".monsters-container[_ngcontent-%COMP%]{padding:20px 0}.monster-grid[_ngcontent-%COMP%]{display:grid;grid-template-columns:repeat(auto-fill,minmax(280px,1fr));gap:20px;margin-top:20px}.monster-card[_ngcontent-%COMP%]{display:flex;flex-direction:column;transition:transform .3s ease}.monster-card[_ngcontent-%COMP%]:hover{transform:translateY(-5px)}.monster-header[_ngcontent-%COMP%]{display:flex;justify-content:space-between;align-items:center;padding-bottom:10px;margin-bottom:15px;border-bottom:2px solid #333}.monster-header.fire[_ngcontent-%COMP%]{border-color:var(--fire-color)}.monster-header.water[_ngcontent-%COMP%]{border-color:var(--water-color)}.monster-header.wind[_ngcontent-%COMP%]{border-color:var(--wind-color)}.element-badge[_ngcontent-%COMP%]{padding:3px 8px;border-radius:4px;font-size:.8rem;font-weight:600}.fire[_ngcontent-%COMP%]   .element-badge[_ngcontent-%COMP%]{background-color:#ff572233;color:var(--fire-color)}.water[_ngcontent-%COMP%]   .element-badge[_ngcontent-%COMP%]{background-color:#2196f333;color:var(--water-color)}.wind[_ngcontent-%COMP%]   .element-badge[_ngcontent-%COMP%]{background-color:#4caf5033;color:var(--wind-color)}.monster-stats[_ngcontent-%COMP%]{flex:1}.stat[_ngcontent-%COMP%]{display:flex;justify-content:space-between;margin-bottom:8px}.stat-label[_ngcontent-%COMP%]{font-weight:500}.monster-actions[_ngcontent-%COMP%]{margin-top:15px;text-align:center}"]})}}return r})();export{R as MonstersComponent};

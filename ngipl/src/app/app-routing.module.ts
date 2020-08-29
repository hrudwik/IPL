import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomePageComponent } from './home-page/home-page.component';
import { PrivateLeagueComponent } from './private-league/private-league.component';
import { RegistrationComponent } from './registration/registration.component';
import { PredictComponent } from './predict/predict.component';
import { LeaderboardComponent } from './leaderboard/leaderboard.component';
import { RulesComponent } from './rules/rules.component';
import { LogoutComponent } from './logout/logout.component';
import { WalletComponent } from './wallet/wallet.component';

const routes: Routes = [
  {path:'', redirectTo: 'home', pathMatch:'full'},
  {path:'login', component:LoginComponent},
  {path:'registration', component:RegistrationComponent},
  {path:'logout', component:LogoutComponent},
  {path:'home', component:HomePageComponent, children: [
    {path:'predict', component:PredictComponent},
    {path:'privateLeague', component:PrivateLeagueComponent},
    {path:'leaderboard', component:LeaderboardComponent},
    {path:'rules', component:RulesComponent},
    {path:'wallet', component:WalletComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

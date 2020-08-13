import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { DatePipe } from '@angular/common'
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HomePageComponent } from './home-page/home-page.component';
import { PredictComponent } from './predict/predict.component';
import { LeaderboardComponent } from './leaderboard/leaderboard.component';
import { RulesComponent } from './rules/rules.component';
import { PrivateLeagueComponent } from './private-league/private-league.component';
import { PredictFormModalComponent } from './predict-form-modal/predict-form-modal.component';
import { LogoutComponent } from './logout/logout.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    HomePageComponent,
    PredictComponent,
    LeaderboardComponent,
    RulesComponent,
    PrivateLeagueComponent,
    PredictFormModalComponent,
    LogoutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent],
  entryComponents: [
    PredictFormModalComponent
  ]
})
export class AppModule { }

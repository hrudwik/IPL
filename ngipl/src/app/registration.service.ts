import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from './user';
import { UserPrediction, UserScorecard } from './domain/user-prediction';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private _http: HttpClient) { }

  public loginUserFromRemote(user: User):Observable<any> {
    return this._http.post<any>("http://192.168.0.109:8080/login", user);
  }

  public registerUserFromRemote(user: User):Observable<any> {
    return this._http.post<any>("http://192.168.0.109:8080/registeruser", user);
  }

  public nextThreeMatchDetailsFromRemote():Observable<any> {
    return this._http.post<any>("http://192.168.0.109:8080/nextThreeMatchDetails", null);
  }

  public updateUserPredictionFromRemote(userPrediction: UserPrediction):Observable<any> {
    return this._http.post<any>("http://192.168.0.109:8080/updateUserPrediction", userPrediction);
  }

  public getUserPredictionFromRemote(userPrediction: UserPrediction):Observable<any> {
    return this._http.post<any>("http://192.168.0.109:8080/getUserPrediction", userPrediction);
  }

  public getOverallLeaderboardFromRemote():Observable<any> {
    return this._http.post<any>("http://192.168.0.109:8080/getOverallLeaderboard", '');
  }
}
